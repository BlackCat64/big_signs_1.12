package net.blackcat64.bigsigns.network;

import io.netty.buffer.ByteBuf;
import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateOneLineSign implements IMessage {
    private BlockPos pos;
    private String text;

    public PacketUpdateOneLineSign() {}

    public PacketUpdateOneLineSign(BlockPos pos, ITextComponent component) {
        this.pos = pos;
        this.text = component.getUnformattedText();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);
        buffer.writeString(text);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        this.pos = buffer.readBlockPos();
        this.text = buffer.readString(384);
    }

    public static class Handler implements IMessageHandler<PacketUpdateOneLineSign, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateOneLineSign message, MessageContext ctx) {
            WorldServer server = ctx.getServerHandler().player.getServerWorld();

            server.addScheduledTask(() -> {
                IBlockState state = server.getBlockState(message.pos);
                TileEntity te = ctx.getServerHandler().player.world.getTileEntity(message.pos);
                if (te instanceof TileEntityOneLineSign) {
                    TileEntityOneLineSign sign = (TileEntityOneLineSign) te;

                    sign.signText[0] = new TextComponentString(
                            TextFormatting.getTextWithoutFormattingCodes(message.text)
                    );
                    sign.signText[1] = new TextComponentString("");
                    sign.signText[2] = new TextComponentString("");
                    sign.signText[3] = new TextComponentString("");

                    sign.markDirty();



                    ctx.getServerHandler().player.world.notifyBlockUpdate(message.pos, state, state, 3);
                }
            });
            return null;
        }
    }
}
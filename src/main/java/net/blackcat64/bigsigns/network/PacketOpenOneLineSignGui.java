package net.blackcat64.bigsigns.network;

import io.netty.buffer.ByteBuf;
import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.blackcat64.bigsigns.gui.GuiEditOneLineSign;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenOneLineSignGui implements IMessage {
    private BlockPos pos;

    public PacketOpenOneLineSignGui() {}

    public PacketOpenOneLineSignGui(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        this.pos = buffer.readBlockPos();
    }

    public static class Handler implements IMessageHandler<PacketOpenOneLineSignGui, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenOneLineSignGui message, MessageContext ctx) {
            System.out.println("Open OneLineSign GUI packet received");
            Minecraft.getMinecraft().addScheduledTask(() -> {
                BlockPos pos = message.pos;
                TileEntity tileEntity = Minecraft.getMinecraft().world.getTileEntity(pos);

                if (!(tileEntity instanceof TileEntityOneLineSign))
                {
                    System.out.println("Creating tile entity");
                    tileEntity = new TileEntityOneLineSign();
                    tileEntity.setWorld(Minecraft.getMinecraft().world);
                    tileEntity.setPos(message.pos);
                }
                else System.out.println("Retrieved tile entity");

                System.out.println("Opening GUI");
                Minecraft.getMinecraft().displayGuiScreen(new GuiEditOneLineSign((TileEntityOneLineSign) tileEntity));
            });
            return null;

        }
    }
}
package net.blackcat64.bigsigns.handlers;

import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SignInteractHandler {
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();

        // Send open-GUI packet from SERVER SIDE
        // + Only when player is crouching (to allow command execution for normal right-click)
        // + Only with empty hand, so that blocks can still be placed on the sign
        if (!world.isRemote
                && player.isSneaking()
                && player.getHeldItemMainhand().isEmpty()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntitySign && !(tileEntity instanceof TileEntityOneLineSign)) {
                TileEntitySign sign = (TileEntitySign) tileEntity;

                sign.setEditable(true);
                sign.setPlayer(player);
                sign.markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                player.openEditSign(sign);



                event.setCanceled(true);
                event.setCancellationResult(EnumActionResult.SUCCESS);
            }
        }
    }
}

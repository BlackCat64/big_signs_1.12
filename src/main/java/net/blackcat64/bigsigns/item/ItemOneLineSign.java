package net.blackcat64.bigsigns.item;

import net.blackcat64.bigsigns.block.BlockOneLineStandingSign;
import net.blackcat64.bigsigns.block.BlockOneLineWallSign;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemOneLineSign extends Item {
    private final Block standing;
    private final Block wall;

    public ItemOneLineSign(Block standing, Block wall) {
        this.standing = standing;
        this.wall = wall;

        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        setRegistryName("one_line_sign");
        setUnlocalizedName("one_line_sign");

        ModItems.ITEMS.add(this);
    }

    // Copied from vanilla ItemSign class, but made generic with 'wall' and 'standing' fields
    public EnumActionResult onItemUse(EntityPlayer player,
                                      World world,
                                      BlockPos pos,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      float hitX,
                                      float hitY,
                                      float hitZ) {

        IBlockState stateAtPos = world.getBlockState(pos);
        boolean isReplaceable = stateAtPos.getBlock().isReplaceable(world, pos);

        if (facing != EnumFacing.DOWN &&
                (stateAtPos.getMaterial().isSolid() || isReplaceable) &&
                (!isReplaceable || facing == EnumFacing.UP)) {

            pos = pos.offset(facing);
            ItemStack heldItem = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, heldItem)
                    && standing.canPlaceBlockAt(world, pos)) {
                if (world.isRemote) {
                    return EnumActionResult.SUCCESS;
                }
                else {
                    pos = isReplaceable ? pos.down() : pos;
                    if (facing == EnumFacing.UP) {
                        int rotation =
                                MathHelper.floor(
                                        (double) ((player.rotationYaw + 180.0F)
                                                * 16.0F / 360.0F)
                                                + 0.5D
                                ) & 15;
                        world.setBlockState(
                                pos,
                                standing.getDefaultState()
                                        .withProperty(BlockOneLineStandingSign.ROTATION, rotation),
                                11
                        );
                    } else {
                        world.setBlockState(
                                pos,
                                wall.getDefaultState()
                                        .withProperty(BlockOneLineWallSign.FACING, facing),
                                11
                        );
                    }
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(world, player, pos, heldItem)) {
                        player.openEditSign((TileEntitySign) tileEntity);
                    }
                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger(
                                (EntityPlayerMP) player,
                                pos,
                                heldItem
                        );
                    }
                    heldItem.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
            else {
                return EnumActionResult.FAIL;
            }
        }
        else {
            return EnumActionResult.FAIL;
        }
    }
}

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
    public EnumActionResult onItemUse(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
        IBlockState lvt_9_1_ = p_180614_2_.getBlockState(p_180614_3_);
        boolean lvt_10_1_ = lvt_9_1_.getBlock().isReplaceable(p_180614_2_, p_180614_3_);
        if (p_180614_5_ != EnumFacing.DOWN && (lvt_9_1_.getMaterial().isSolid() || lvt_10_1_) && (!lvt_10_1_ || p_180614_5_ == EnumFacing.UP)) {
            p_180614_3_ = p_180614_3_.offset(p_180614_5_);
            ItemStack lvt_11_1_ = p_180614_1_.getHeldItem(p_180614_4_);
            if (p_180614_1_.canPlayerEdit(p_180614_3_, p_180614_5_, lvt_11_1_) && standing.canPlaceBlockAt(p_180614_2_, p_180614_3_)) {
                if (p_180614_2_.isRemote) {
                    return EnumActionResult.SUCCESS;
                } else {
                    p_180614_3_ = lvt_10_1_ ? p_180614_3_.down() : p_180614_3_;
                    if (p_180614_5_ == EnumFacing.UP) {
                        int lvt_12_1_ = MathHelper.floor((double)((p_180614_1_.rotationYaw + 180.0F) * 16.0F / 360.0F) + (double)0.5F) & 15;
                        p_180614_2_.setBlockState(p_180614_3_, standing.getDefaultState().withProperty(BlockOneLineStandingSign.ROTATION, lvt_12_1_), 11);
                    } else {
                        p_180614_2_.setBlockState(p_180614_3_, wall.getDefaultState().withProperty(BlockOneLineWallSign.FACING, p_180614_5_), 11);
                    }

                    TileEntity lvt_12_2_ = p_180614_2_.getTileEntity(p_180614_3_);
                    if (lvt_12_2_ instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(p_180614_2_, p_180614_1_, p_180614_3_, lvt_11_1_)) {
                        p_180614_1_.openEditSign((TileEntitySign)lvt_12_2_);
                    }

                    if (p_180614_1_ instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)p_180614_1_, p_180614_3_, lvt_11_1_);
                    }

                    lvt_11_1_.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }
}

package net.blackcat64.bigsigns.block;

import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.blackcat64.bigsigns.item.ModItems;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOneLineWallSign extends BlockWallSign {
    public BlockOneLineWallSign() {
        setRegistryName("one_line_wall_sign");
        setUnlocalizedName("one_line_wall_sign");
        setHardness(1.0f);
        setResistance(1.0f);

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.ONE_LINE_SIGN;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModItems.ONE_LINE_SIGN);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOneLineSign();
    }
}

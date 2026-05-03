package net.blackcat64.bigsigns.block;

import net.blackcat64.bigsigns.BigSignsMod;
import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.blackcat64.bigsigns.gui.GuiEditOneLineSign;
import net.blackcat64.bigsigns.item.ModItems;
import net.blackcat64.bigsigns.network.PacketOpenOneLineSignGui;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOneLineStandingSign extends BlockStandingSign {
    public BlockOneLineStandingSign() {
        setRegistryName("one_line_sign");
        setUnlocalizedName("one_line_sign");
        setHardness(1.0f);
        setResistance(1.0f);
        setSoundType(SoundType.WOOD);

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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityOneLineSign) {
                BigSignsMod.NETWORK.sendTo(new PacketOpenOneLineSignGui(pos),
                        (EntityPlayerMP) playerIn
                );
            }
            return true;
        }
    }
}

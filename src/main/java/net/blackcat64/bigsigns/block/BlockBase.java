package net.blackcat64.bigsigns.block;

import net.blackcat64.bigsigns.BigSignsMod;
import net.blackcat64.bigsigns.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {
    public BlockBase(String registryName, Material material) {
        super(material);
        setRegistryName(registryName);
        setUnlocalizedName(registryName);
        setCreativeTab(CreativeTabs.DECORATIONS);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}

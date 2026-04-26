package net.blackcat64.bigsigns.item;

import net.blackcat64.bigsigns.BigSignsMod;
import net.blackcat64.bigsigns.proxy.ClientProxy;
import net.blackcat64.bigsigns.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String registryName) {
        setUnlocalizedName(registryName);
        setRegistryName(BigSignsMod.MODID, registryName);
        setCreativeTab(CreativeTabs.DECORATIONS);

        ModItems.ITEMS.add(this);
    }


    @Override
    public void registerModels() {
        BigSignsMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

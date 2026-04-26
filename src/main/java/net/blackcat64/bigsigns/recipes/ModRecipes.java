package net.blackcat64.bigsigns.recipes;

import net.blackcat64.bigsigns.BigSignsMod;
import net.blackcat64.bigsigns.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void init() {
        GameRegistry.addShapelessRecipe(
                new ResourceLocation(BigSignsMod.MODID, "one_line_sign"),
                null,
                new ItemStack(ModItems.ONE_LINE_SIGN),
                Ingredient.fromItem(Items.SIGN)
        );

        GameRegistry.addShapelessRecipe(
                new ResourceLocation(BigSignsMod.MODID, "one_line_sign_reverse"),
                null,
                new ItemStack(Items.SIGN),
                Ingredient.fromItem(ModItems.ONE_LINE_SIGN)
        );
    }
}

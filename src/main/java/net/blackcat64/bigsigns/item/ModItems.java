package net.blackcat64.bigsigns.item;

import net.blackcat64.bigsigns.block.ModBlocks;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

//    public static final Item TEST_ITEM = new ItemBase("test_item");

    public static Item ONE_LINE_SIGN;

    public static void init() {
        ONE_LINE_SIGN = new ItemOneLineSign(
                ModBlocks.ONE_LINE_STANDING_SIGN,
                ModBlocks.ONE_LINE_WALL_SIGN
        );
    }
}

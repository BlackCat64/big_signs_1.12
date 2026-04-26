package net.blackcat64.bigsigns.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

//    public static final Block TEST_BLOCK = new BlockBase("test_block", Material.ANVIL);

    public static Block ONE_LINE_STANDING_SIGN;
    public static Block ONE_LINE_WALL_SIGN;

    public static void init() {
        ONE_LINE_STANDING_SIGN = new BlockOneLineStandingSign();
        ONE_LINE_WALL_SIGN = new BlockOneLineWallSign();
    }
}

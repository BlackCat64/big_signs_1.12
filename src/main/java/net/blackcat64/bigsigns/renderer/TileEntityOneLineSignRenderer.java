package net.blackcat64.bigsigns.renderer;

import net.blackcat64.bigsigns.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class TileEntityOneLineSignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
    private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
    private final ModelSign model = new ModelSign();

    public TileEntityOneLineSignRenderer() {
    }

    public void render(TileEntitySign p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        Block lvt_11_1_ = p_192841_1_.getBlockType();
        GlStateManager.pushMatrix();
        float lvt_12_1_ = 0.6666667F;
        if (lvt_11_1_ == ModBlocks.ONE_LINE_STANDING_SIGN) {
            GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
            float lvt_13_1_ = (float)(p_192841_1_.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(-lvt_13_1_, 0.0F, 1.0F, 0.0F);
            this.model.signStick.showModel = true;
        } else if (lvt_11_1_ == ModBlocks.ONE_LINE_WALL_SIGN) {
            int lvt_13_2_ = p_192841_1_.getBlockMetadata();
            float lvt_14_1_ = 0.0F;
            if (lvt_13_2_ == 2) {
                lvt_14_1_ = 180.0F;
            }

            if (lvt_13_2_ == 4) {
                lvt_14_1_ = 90.0F;
            }

            if (lvt_13_2_ == 5) {
                lvt_14_1_ = -90.0F;
            }

            GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
            GlStateManager.rotate(-lvt_14_1_, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            this.model.signStick.showModel = false;
        }
        else return; // Failsafe in case of unknown block type

        if (p_192841_9_ >= 0) {
            this.bindTexture(DESTROY_STAGES[p_192841_9_]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(SIGN_TEXTURE);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
        this.model.renderSign();
        GlStateManager.popMatrix();
        FontRenderer lvt_13_3_ = this.getFontRenderer();
        float lvt_14_2_ = 0.010416667F;
        GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
        GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
        GlStateManager.depthMask(false);
        int lvt_15_1_ = 0;
        if (p_192841_9_ < 0) {
            for(int lvt_16_1_ = 0; lvt_16_1_ < p_192841_1_.signText.length; ++lvt_16_1_) {
                if (p_192841_1_.signText[lvt_16_1_] != null) {
                    ITextComponent lvt_17_1_ = p_192841_1_.signText[lvt_16_1_];
                    List<ITextComponent> lvt_18_1_ = GuiUtilRenderComponents.splitText(lvt_17_1_, 90, lvt_13_3_, false, true);
                    String lvt_19_1_ = lvt_18_1_ != null && !lvt_18_1_.isEmpty() ? ((ITextComponent)lvt_18_1_.get(0)).getFormattedText() : "";
                    if (lvt_16_1_ == p_192841_1_.lineBeingEdited) {
                        lvt_19_1_ = "> " + lvt_19_1_ + " <";
                        lvt_13_3_.drawString(lvt_19_1_, -lvt_13_3_.getStringWidth(lvt_19_1_) / 2, lvt_16_1_ * 10 - p_192841_1_.signText.length * 5, 0);
                    } else {
                        lvt_13_3_.drawString(lvt_19_1_, -lvt_13_3_.getStringWidth(lvt_19_1_) / 2, lvt_16_1_ * 10 - p_192841_1_.signText.length * 5, 0);
                    }
                }
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        if (p_192841_9_ >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

    }
}

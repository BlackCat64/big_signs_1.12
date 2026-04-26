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

    public void render(TileEntitySign tileEntity,
                       double x,
                       double y,
                       double z,
                       float partialTicks,
                       int destroyStage,
                       float alpha) {

        Block block = tileEntity.getBlockType();

        GlStateManager.pushMatrix();

        if (block == ModBlocks.ONE_LINE_STANDING_SIGN) {

            GlStateManager.translate((float) x + 0.5F,
                    (float) y + 0.5F,
                    (float) z + 0.5F);

            float rotation = (float) (tileEntity.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(-rotation, 0.0F, 1.0F, 0.0F);

            this.model.signStick.showModel = true;

        } else if (block == ModBlocks.ONE_LINE_WALL_SIGN) {

            int meta = tileEntity.getBlockMetadata();
            float facingRotation = 0.0F;

            if (meta == 2) {
                facingRotation = 180.0F;
            }

            if (meta == 4) {
                facingRotation = 90.0F;
            }

            if (meta == 5) {
                facingRotation = -90.0F;
            }

            GlStateManager.translate((float) x + 0.5F,
                    (float) y + 0.5F,
                    (float) z + 0.5F);

            GlStateManager.rotate(-facingRotation, 0.0F, 1.0F, 0.0F);

            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);

            this.model.signStick.showModel = false;

        } else return; // Failsafe in case of unknown block type

        if (destroyStage >= 0) {

            this.bindTexture(DESTROY_STAGES[destroyStage]);

            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();

            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);

            GlStateManager.matrixMode(5888);

        } else {
            this.bindTexture(SIGN_TEXTURE);
        }

        GlStateManager.enableRescaleNormal();

        float modelScale = 0.6666667F;
        GlStateManager.pushMatrix();
        GlStateManager.scale(modelScale, modelScale * -1.0F, modelScale * -1.0F);

        this.model.renderSign();

        GlStateManager.popMatrix();

        FontRenderer fontRenderer = this.getFontRenderer();

        float textScale = 0.010416667F;
        textScale = textScale * 4.95F; // Match the 1.20 version of the mod - Exactly 4.5 times larger text

        GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scale(textScale, textScale * -1.0F, textScale);

        GlStateManager.glNormal3f(0.0F, 0.0F, textScale * -1.0F);

        GlStateManager.depthMask(false);

        int lineIndex = 0;

        if (destroyStage < 0) {

            for (int i = 0; i < tileEntity.signText.length; ++i) {

                if (tileEntity.signText[i] != null) {

                    ITextComponent textComponent = tileEntity.signText[i];

                    List<ITextComponent> splitText =
                            GuiUtilRenderComponents.splitText(textComponent, 90, fontRenderer, false, true);

                    String lineText = splitText != null && !splitText.isEmpty()
                            ? splitText.get(0).getFormattedText()
                            : "";

                    if (i == tileEntity.lineBeingEdited) {
                        lineText = "> " + lineText + " <";
                    }

                    fontRenderer.drawString(
                            lineText,
                            -fontRenderer.getStringWidth(lineText) / 2,
                            -4,
                            0
                    );
                }
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.popMatrix();

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}

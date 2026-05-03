package net.blackcat64.bigsigns.gui;


import net.blackcat64.bigsigns.BigSignsMod;
import net.blackcat64.bigsigns.block.ModBlocks;
import net.blackcat64.bigsigns.block.entity.TileEntityOneLineSign;
import net.blackcat64.bigsigns.network.PacketUpdateOneLineSign;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiEditOneLineSign extends GuiEditSign {
    private final TileEntityOneLineSign tileSign;
    private int updateCounter;
    private static final int EDIT_LINE = 0;
    private GuiButton doneBtn;

    public GuiEditOneLineSign(TileEntityOneLineSign tileEntitySign) {
        super(tileEntitySign);
        this.tileSign = tileEntitySign;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
        this.tileSign.setEditable(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        System.out.println("Sending sign update packet: '" + this.tileSign.signText[0] + "'");

        BigSignsMod.NETWORK.sendToServer(new PacketUpdateOneLineSign(this.tileSign.getPos(), this.tileSign.signText[0]));

        this.tileSign.setEditable(true);
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.tileSign.markDirty();
                this.mc.displayGuiScreen((GuiScreen)null);
            }

        }
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) throws IOException {
        // Just to be safe
        if (this.tileSign.signText[EDIT_LINE] == null) {
            this.tileSign.signText[EDIT_LINE] = new TextComponentString("");
        }

        String text = this.tileSign.signText[EDIT_LINE].getUnformattedText();
        if (keyCode == 14 && !text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(keyChar) && this.fontRenderer.getStringWidth(text + keyChar) <= 20) {
            text = text + keyChar;
        }

        this.tileSign.signText[EDIT_LINE] = new TextComponentString(text);
        if (keyCode == 1) {
            this.actionPerformed(this.doneBtn);
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);

        // Scale text size by 4.95x
        float textScale = 93.75F;
        textScale = textScale * 4.95F;
        textScale = textScale * -1.0F; // Value must be negative
        GlStateManager.scale(textScale, textScale, textScale);

        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        Block signBlock = this.tileSign.getBlockType();
        if (signBlock == ModBlocks.ONE_LINE_STANDING_SIGN) {
            float lvt_6_1_ = (float)(this.tileSign.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(lvt_6_1_, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        }
        else if (signBlock == ModBlocks.ONE_LINE_WALL_SIGN) {
            int meta = this.tileSign.getBlockMetadata();
            float rot = 0.0F;
            if (meta == 2) {
                rot = 180.0F;
            }

            if (meta == 4) {
                rot = 90.0F;
            }

            if (meta == 5) {
                rot = -90.0F;
            }

            GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        }

        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = EDIT_LINE;
        }

        TileEntityRendererDispatcher.instance.render(this.tileSign, (double)-0.5F, (double)-0.75F, (double)-0.5F, 0.0F);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

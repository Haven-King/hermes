package dev.inkwell.hermes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5489;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class WarningScreen extends Screen {
    private final Text reason;
    private class_5489 reasonFormatted;
    private final Screen parent;
    private int reasonHeight;

    public WarningScreen(Screen parent, Text text, Text reason) {
        super(text);
        this.reasonFormatted = class_5489.field_26528; // I fuckin hate this class
        this.parent = parent;
        this.reason = reason;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        this.reasonFormatted = class_5489.method_30890(this.textRenderer, this.reason, this.width - 50);
        int var10001 = this.reasonFormatted.method_30887();

        if (this.textRenderer != null && this.client != null) {
            this.reasonHeight = var10001 * 9;
            int buttonWidth = this.width / 2 - 100;
            int buttonHeight = this.height / 2 + this.reasonHeight / 2;

            this.addButton(new ButtonWidget(buttonWidth, Math.min(buttonHeight + 9, this.height - 30), 200, 20, new LiteralText("Ok"), (buttonWidget) -> {
                this.client.openScreen(this.parent);
            }));
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        if (this.textRenderer != null) {
            int var10003 = this.width / 2;
            int var10004 = this.height / 2 - this.reasonHeight / 2;
            drawCenteredText(matrices, this.textRenderer, this.title, var10003, var10004 - 9 * 2, 11184810);
            this.reasonFormatted.method_30888(matrices, this.width / 2, this.height / 2 - this.reasonHeight / 2);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }
}

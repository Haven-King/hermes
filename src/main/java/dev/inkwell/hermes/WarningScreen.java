package dev.inkwell.hermes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.List;

@Environment(EnvType.CLIENT)
public class WarningScreen extends Screen {
    private final List<String> reason;
    private final Screen parent;
    private int reasonHeight;

    public WarningScreen(Screen parent, Text text, List<String> reason) {
        super(text);
        this.parent = parent;
        this.reason = reason;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        if (this.textRenderer != null && this.client != null) {
            this.reasonHeight = this.reason.size() * 9;
            int buttonWidth = this.width / 2 - 100;
            int buttonHeight = this.height / 2 + this.reasonHeight / 2;

            this.addDrawableChild(new ButtonWidget(buttonWidth, Math.min(buttonHeight + 9, this.height - 30), 200, 20, new LiteralText("Ok"), (buttonWidget) -> this.client.openScreen(this.parent)));
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        if (this.textRenderer != null) {
            int centerX = this.width / 2;
            int centerY = this.height / 2 - this.reasonHeight / 2;
            drawCenteredText(matrices, this.textRenderer, this.title, centerX, centerY - 9 * 2, 0xaaaaaa);

            for (int i = 0; i < this.reason.size(); ++i) {
                drawCenteredText(matrices, this.textRenderer, new LiteralText(this.reason.get(i)), centerX, centerY - 9 * 2 + 12 * (i + 1), 0xFFFFFFFF);
            }
        }

        super.render(matrices, mouseX, mouseY, delta);
    }
}

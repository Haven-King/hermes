package dev.inkwell.hermes.mixin;

import dev.inkwell.hermes.Hermes;
import dev.inkwell.hermes.WarningScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(value = MinecraftClient.class, priority = 1337)
public abstract class MixinMinecraftClient {
    @Shadow public abstract void openScreen(@Nullable Screen screen);

    @Shadow @Nullable public Screen currentScreen;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void openWarningScreen(RunArgs args, CallbackInfo ci) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Collection<ModContainer> viewedMods = Hermes.getViewedMods();
            Collection<ModContainer> mods = Hermes.getMods();

            boolean openScreen = false;

            for (ModContainer container : mods) {
                if (!viewedMods.contains(container)) {
                    openScreen = true;
                }

                viewedMods.add(container);
            }

            if (openScreen) {
                this.openScreen(new WarningScreen(
                        this.currentScreen,
                        new LiteralText("The following mods/libraries should not be JiJ'd:"),
                        mods.stream().map(container -> container.getMetadata().getName()).collect(Collectors.toList())));
            }

            Hermes.save(viewedMods);
        }
    }
}

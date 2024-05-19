package com.malaclord.theshire.mixin.client;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "disconnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;<init>()V", shift = At.Shift.BEFORE), cancellable = true)
    public void injectedDisconnect(CallbackInfo ci) {
        TitleScreen titleScreen = new TitleScreen();

        assert this.client != null;
        this.client.setScreen(titleScreen);

        ci.cancel();
    }
}

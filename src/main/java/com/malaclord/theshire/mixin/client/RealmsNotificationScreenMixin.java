package com.malaclord.theshire.mixin.client;

import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RealmsNotificationsScreen.class)
public class RealmsNotificationScreenMixin extends RealmsScreen {
    protected RealmsNotificationScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void iHateRealms(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onDisplayed", at = @At("HEAD"), cancellable = true)
    public void iHateItEvenMore(CallbackInfo ci) {
        ci.cancel();
    }
}

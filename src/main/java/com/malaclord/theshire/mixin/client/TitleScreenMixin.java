package com.malaclord.theshire.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    @Unique
    private final ServerInfo shireInfo = new ServerInfo("The Shire","play.shirecraft.us", ServerInfo.ServerType.OTHER);

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    private Text getMultiplayerDisabledText() {
        return null;
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void initInjected(CallbackInfo ci) {

    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/PressableTextWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;Lnet/minecraft/client/font/TextRenderer;)V"), index = 1)
    public int modifyCopyrightText(int x) {

        return x + 12;
    }

    @Inject(method = "initWidgetsNormal", at = @At("HEAD"), cancellable = true)
    public void initWidgetsNormalInjected(int y, int spacingY, CallbackInfo ci) {
        Text text = this.getMultiplayerDisabledText();
        boolean bl = text == null;
        Tooltip tooltip = text != null ? Tooltip.of(text) : null;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("The Shire").formatted(Formatting.BOLD), (button) ->
                ConnectScreen.connect(this, MinecraftClient.getInstance(), ServerAddress.parse("play.shirecraft.us"),shireInfo,true))
                .dimensions(this.width / 2 - 100, y, 200, 32 + spacingY).tooltip(tooltip).build()).active = bl;


        this.addDrawableChild(ButtonWidget.builder(Text.literal("The Shire Survival"), (button) ->
                ConnectScreen.connect(this, MinecraftClient.getInstance(), ServerAddress.parse("survival.shirecraft.us"),shireInfo,true))
                .dimensions(this.width / 2 - 100, y + spacingY * 2 + 12, 98, 20).tooltip(tooltip).build()).active = bl;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) -> {
            assert this.client != null;
            this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(this.width / 2 + 2, y + spacingY * 2 + 12, 98, 20).build());

        ci.cancel();
    }
}

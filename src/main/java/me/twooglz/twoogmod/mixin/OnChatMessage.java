package me.twooglz.twoogmod.mixin;


import me.twooglz.twoogmod.TwoogMod;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;

@Mixin(MessageHandler.class)
public class OnChatMessage {

    @Inject(method = "addToChatLog(Lnet/minecraft/text/Text;Ljava/time/Instant;)V", at = @At("TAIL"))
    void onMessage(Text message, Instant timestamp, CallbackInfo ci) {
        TwoogMod.checkForAutoTPA(message.getString());
    }
}

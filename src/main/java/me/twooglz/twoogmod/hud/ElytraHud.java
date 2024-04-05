package me.twooglz.twoogmod.hud;

import me.twooglz.twoogmod.Speedometer;
import me.twooglz.twoogmod.TwoogMod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ElytraHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Window mainWindow = MinecraftClient.getInstance().getWindow();

        if (Speedometer.speed == null) return;

        float pitch = MinecraftClient.getInstance().player.getPitch();

//        TwoogMod.twoogLog(String.valueOf(xVelocity));
//        TwoogMod.twoogLog(String.valueOf(zVelocity));
        double xZVelocity = sqrt(pow(Speedometer.speed.x, 2) + pow(Speedometer.speed.z, 2));
        double yVelocity = -Speedometer.speed.y;
        double flightPathAngle = Math.toDegrees(Math.atan(yVelocity/xZVelocity));
        double yLevel = MinecraftClient.getInstance().player.getY();


        new HUDText()
            .line(String.format("§b%.2f°", pitch)) // Pitch
            .line(String.format("§b%.2f°", flightPathAngle)) // Flight path angle
            .draw(
                drawContext,
                HUDText.position.CENTER,
                HUDText.anchor.RIGHT,
                20,
                0
            );

        new HUDText()
            .line(String.format("§e%.2f m/s", xZVelocity))
            .line(String.format("§b%.2f m/s", yVelocity))
            .draw(
                drawContext,
                HUDText.position.CENTER,
                HUDText.anchor.LEFT,
                20,
                0
            );

        new HUDText()
            .line(String.format("§a%.0f", yLevel))
            .draw(
                drawContext,
                HUDText.position.CENTER,
                HUDText.anchor.TOP,
                0,
                10
            );
    }
}

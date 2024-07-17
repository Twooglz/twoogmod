package me.twooglz.twoogmod.hud;

import me.twooglz.twoogmod.Speedometer;
import me.twooglz.twoogmod.TwoogConfig;
import me.twooglz.twoogmod.TwoogMod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.awt.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ElytraHud implements HudRenderCallback {

    public static boolean enabled;

    float pitch;
    double xZVelocity;
    double yVelocity;
    double flightPathAngle;
    double yLevel;


    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {

        if (!MinecraftClient.getInstance().player.isFallFlying() || !TwoogMod.CONFIG.elytraHudEnabled()) return;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Window mainWindow = MinecraftClient.getInstance().getWindow();

        if (Speedometer.speed == null) return;

        float pitch = MinecraftClient.getInstance().player.getPitch();

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

        String horizontalColor;
        if (xZVelocity > TwoogMod.CONFIG.elytraHudHSpeedGreenThresh()){
            horizontalColor = "§a";
        } else if (xZVelocity > TwoogMod.CONFIG.elytraHudHSpeedYellowThresh()) {
            horizontalColor = "§e";
        } else {
            horizontalColor = "§c";
        }

        String verticalColor;
        if (yVelocity > TwoogMod.CONFIG.elytraHudVSpeedGreenThresh()){
            verticalColor = "§a";
        } else if (yVelocity > TwoogMod.CONFIG.elytraHudVSpeedYellowThresh()) {
            verticalColor = "§e";
        } else {
            verticalColor = "§c";
        }

        new HUDText()
            .line(String.format(horizontalColor + "%.2f m/s", xZVelocity)) // Horizontal velocity (colored)
            .line(String.format(verticalColor + "%.2f m/s", yVelocity)) // Vertical velocity (colored)
            .draw(
                drawContext,
                HUDText.position.CENTER,
                HUDText.anchor.LEFT,
                20,
                0
            );
        String yColor;

        if (MinecraftClient.getInstance().world.getDimensionEntry().getIdAsString().equals("minecraft:overworld")) {
            if (yLevel > TwoogMod.CONFIG.elytraHudYLevelOverworldGreenThresh()){
                yColor = "§a";
            } else if (yLevel > TwoogMod.CONFIG.elytraHudYLevelOverworldYellowThresh()) {
                yColor = "§e";
            } else {
                yColor = "§c";
            }
        } else if (MinecraftClient.getInstance().world.getDimensionEntry().getIdAsString().equals("minecraft:the_nether")) {
            if (yLevel > TwoogMod.CONFIG.elytraHudYLevelNetherGreenThresh()){
                yColor = "§a";
            } else if (yLevel > TwoogMod.CONFIG.elytraHudYLevelNetherYellowThresh()) {
                yColor = "§e";
            } else {
                yColor = "§c";
            }
        } else if (MinecraftClient.getInstance().world.getDimensionEntry().getIdAsString().equals("minecraft:the_end")) {
            if (yLevel > TwoogMod.CONFIG.elytraHudYLevelEndGreenThresh()){
                yColor = "§a";
            } else if (yLevel > TwoogMod.CONFIG.elytraHudYLevelEndYellowThresh()) {
                yColor = "§e";
            } else {
                yColor = "§c";
            }
        } else {
            yColor = "§b";
        }

        new HUDText()
            .line(String.format(yColor + "%.0f", yLevel)) // Y level (colored)
            .draw(
                drawContext,
                HUDText.position.CENTER,
                HUDText.anchor.TOP,
                0,
                10
            );
    }

    public void infoDump() {
        TwoogMod.twoogLog("Enable status: " + enabled);
        TwoogMod.twoogLog("Velocity: H: " + xZVelocity + " V: " + yVelocity);
        TwoogMod.twoogLog("Angles: P: " + pitch + " FPA: " + flightPathAngle);
        TwoogMod.twoogLog("y level: " + yLevel);
    }

}

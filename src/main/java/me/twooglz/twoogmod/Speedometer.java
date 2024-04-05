package me.twooglz.twoogmod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Speedometer implements ClientTickEvents.StartTick {
    public static Vec3d pos;
    public static Vec3d posOld;
    public static Vec3d delta;
    public static Vec3d speed;

    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player != null) {
            pos = client.player.getPos();
            if (posOld == null) posOld = pos;
            delta = posOld.subtract(pos);
            speed = delta.multiply(20.0d);
            posOld = pos;
        }
    }
}
package me.twooglz.twoogmod;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "twoogmod")
@Config(name = "twoogmod-config", wrapperName = "TwoogConfig")
public class TwoogConfigModel {
    public boolean elytraHudEnabled = false;

    public float elytraHudYLevelOverworldGreenThresh = 320.0f;
    public float elytraHudYLevelOverworldYellowThresh = 256.0f;
    public float elytraHudYLevelNetherGreenThresh = 180.0f;
    public float elytraHudYLevelNetherYellowThresh = 150.0f;
    public float elytraHudYLevelEndGreenThresh = 320.0f;
    public float elytraHudYLevelEndYellowThresh = 256.0f;

    public float elytraHudHSpeedGreenThresh = 50.0f;
    public float elytraHudHSpeedYellowThresh = 40.0f;

    public float elytraHudVSpeedGreenThresh = -10.0f;
    public float elytraHudVSpeedYellowThresh = -11.0f;


}

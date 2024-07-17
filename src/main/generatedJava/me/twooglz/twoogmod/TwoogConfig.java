package me.twooglz.twoogmod;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TwoogConfig extends ConfigWrapper<me.twooglz.twoogmod.TwoogConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> elytraHudEnabled = this.optionForKey(this.keys.elytraHudEnabled);
    private final Option<java.lang.Float> elytraHudYLevelOverworldGreenThresh = this.optionForKey(this.keys.elytraHudYLevelOverworldGreenThresh);
    private final Option<java.lang.Float> elytraHudYLevelOverworldYellowThresh = this.optionForKey(this.keys.elytraHudYLevelOverworldYellowThresh);
    private final Option<java.lang.Float> elytraHudYLevelNetherGreenThresh = this.optionForKey(this.keys.elytraHudYLevelNetherGreenThresh);
    private final Option<java.lang.Float> elytraHudYLevelNetherYellowThresh = this.optionForKey(this.keys.elytraHudYLevelNetherYellowThresh);
    private final Option<java.lang.Float> elytraHudYLevelEndGreenThresh = this.optionForKey(this.keys.elytraHudYLevelEndGreenThresh);
    private final Option<java.lang.Float> elytraHudYLevelEndYellowThresh = this.optionForKey(this.keys.elytraHudYLevelEndYellowThresh);
    private final Option<java.lang.Float> elytraHudHSpeedGreenThresh = this.optionForKey(this.keys.elytraHudHSpeedGreenThresh);
    private final Option<java.lang.Float> elytraHudHSpeedYellowThresh = this.optionForKey(this.keys.elytraHudHSpeedYellowThresh);
    private final Option<java.lang.Float> elytraHudVSpeedGreenThresh = this.optionForKey(this.keys.elytraHudVSpeedGreenThresh);
    private final Option<java.lang.Float> elytraHudVSpeedYellowThresh = this.optionForKey(this.keys.elytraHudVSpeedYellowThresh);

    private TwoogConfig() {
        super(me.twooglz.twoogmod.TwoogConfigModel.class);
    }

    private TwoogConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(me.twooglz.twoogmod.TwoogConfigModel.class, janksonBuilder);
    }

    public static TwoogConfig createAndLoad() {
        var wrapper = new TwoogConfig();
        wrapper.load();
        return wrapper;
    }

    public static TwoogConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new TwoogConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public boolean elytraHudEnabled() {
        return elytraHudEnabled.value();
    }

    public void elytraHudEnabled(boolean value) {
        elytraHudEnabled.set(value);
    }

    public float elytraHudYLevelOverworldGreenThresh() {
        return elytraHudYLevelOverworldGreenThresh.value();
    }

    public void elytraHudYLevelOverworldGreenThresh(float value) {
        elytraHudYLevelOverworldGreenThresh.set(value);
    }

    public float elytraHudYLevelOverworldYellowThresh() {
        return elytraHudYLevelOverworldYellowThresh.value();
    }

    public void elytraHudYLevelOverworldYellowThresh(float value) {
        elytraHudYLevelOverworldYellowThresh.set(value);
    }

    public float elytraHudYLevelNetherGreenThresh() {
        return elytraHudYLevelNetherGreenThresh.value();
    }

    public void elytraHudYLevelNetherGreenThresh(float value) {
        elytraHudYLevelNetherGreenThresh.set(value);
    }

    public float elytraHudYLevelNetherYellowThresh() {
        return elytraHudYLevelNetherYellowThresh.value();
    }

    public void elytraHudYLevelNetherYellowThresh(float value) {
        elytraHudYLevelNetherYellowThresh.set(value);
    }

    public float elytraHudYLevelEndGreenThresh() {
        return elytraHudYLevelEndGreenThresh.value();
    }

    public void elytraHudYLevelEndGreenThresh(float value) {
        elytraHudYLevelEndGreenThresh.set(value);
    }

    public float elytraHudYLevelEndYellowThresh() {
        return elytraHudYLevelEndYellowThresh.value();
    }

    public void elytraHudYLevelEndYellowThresh(float value) {
        elytraHudYLevelEndYellowThresh.set(value);
    }

    public float elytraHudHSpeedGreenThresh() {
        return elytraHudHSpeedGreenThresh.value();
    }

    public void elytraHudHSpeedGreenThresh(float value) {
        elytraHudHSpeedGreenThresh.set(value);
    }

    public float elytraHudHSpeedYellowThresh() {
        return elytraHudHSpeedYellowThresh.value();
    }

    public void elytraHudHSpeedYellowThresh(float value) {
        elytraHudHSpeedYellowThresh.set(value);
    }

    public float elytraHudVSpeedGreenThresh() {
        return elytraHudVSpeedGreenThresh.value();
    }

    public void elytraHudVSpeedGreenThresh(float value) {
        elytraHudVSpeedGreenThresh.set(value);
    }

    public float elytraHudVSpeedYellowThresh() {
        return elytraHudVSpeedYellowThresh.value();
    }

    public void elytraHudVSpeedYellowThresh(float value) {
        elytraHudVSpeedYellowThresh.set(value);
    }


    public static class Keys {
        public final Option.Key elytraHudEnabled = new Option.Key("elytraHudEnabled");
        public final Option.Key elytraHudYLevelOverworldGreenThresh = new Option.Key("elytraHudYLevelOverworldGreenThresh");
        public final Option.Key elytraHudYLevelOverworldYellowThresh = new Option.Key("elytraHudYLevelOverworldYellowThresh");
        public final Option.Key elytraHudYLevelNetherGreenThresh = new Option.Key("elytraHudYLevelNetherGreenThresh");
        public final Option.Key elytraHudYLevelNetherYellowThresh = new Option.Key("elytraHudYLevelNetherYellowThresh");
        public final Option.Key elytraHudYLevelEndGreenThresh = new Option.Key("elytraHudYLevelEndGreenThresh");
        public final Option.Key elytraHudYLevelEndYellowThresh = new Option.Key("elytraHudYLevelEndYellowThresh");
        public final Option.Key elytraHudHSpeedGreenThresh = new Option.Key("elytraHudHSpeedGreenThresh");
        public final Option.Key elytraHudHSpeedYellowThresh = new Option.Key("elytraHudHSpeedYellowThresh");
        public final Option.Key elytraHudVSpeedGreenThresh = new Option.Key("elytraHudVSpeedGreenThresh");
        public final Option.Key elytraHudVSpeedYellowThresh = new Option.Key("elytraHudVSpeedYellowThresh");
    }
}


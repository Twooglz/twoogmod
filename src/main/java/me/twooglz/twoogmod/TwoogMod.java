package me.twooglz.twoogmod;


import com.mojang.brigadier.arguments.StringArgumentType;
import me.twooglz.twoogmod.hud.ElytraHud;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import me.twooglz.twoogmod.TwoogConfig;


public class TwoogMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("twoogmod");
	public static final TwoogConfig CONFIG = TwoogConfig.createAndLoad();

	@Override
	public void onInitialize() {

		LOGGER.info("Loading TwoogMod");
		ElytraHud elytraHud = new ElytraHud();

		HudRenderCallback.EVENT.register(elytraHud);
		ClientTickEvents.START_CLIENT_TICK.register(new Speedometer());

		// Pack folder

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("pf")
			.executes(context -> {
				System.out.println(FabricLoader.getInstance().getGameDir().toFile().toString());

				Util.getOperatingSystem().open(FabricLoader.getInstance().getGameDir().resolve("resourcepacks").toFile());

				return 1;
			}))
		);

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("getdim")
				.executes(context -> {
					twoogLog(MinecraftClient.getInstance().world.getDimensionEntry().getIdAsString());
					return 1;
				})
		));
	}
	public static void twoogLog(String message) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTM§6§l]§a " + message));
	}

	public static void twoogLogWarn(String message) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTM§6§l]§c " + message));
	}


}
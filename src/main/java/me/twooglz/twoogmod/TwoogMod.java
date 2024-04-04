package me.twooglz.twoogmod;


import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;


public class TwoogMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("twoogmod");

	static boolean autoTPAEnabled = false;
	static ArrayList<UUID> autoTPAWhitelistedUUIDs = new ArrayList<>();




	@Override
	public void onInitialize() {

		LOGGER.info("Loading TwoogMod");

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("zandkoek")
				.then(argument("amount", integer()
					).then(argument("delay (ms)", integer())
						.executes(context -> {
							final int amount = getInteger(context, "amount");
							final int delay = getInteger(context, "delay (ms)");
							ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;

							new Thread(() -> {
								for (int i = 420; i < 420 + amount; i++) {
									twoogLog("Spawning zandkoekje" + i);
                                    assert clientPlayer != null;
                                    clientPlayer.networkHandler.sendChatCommand("player zandkoekje" + i + " spawn");
									try {
										Thread.sleep(delay);
									} catch (InterruptedException e) {
										throw new RuntimeException(e);
									}

								}
							}).start();

							return 1;

						})
					)
				)
			)
		);

		// Pack folder

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("pf")
			.executes(context -> {
				System.out.println(FabricLoader.getInstance().getGameDir().toFile().toString());

				Util.getOperatingSystem().open(FabricLoader.getInstance().getGameDir().resolve("resourcepacks").toFile());

				return 1;
			}))
		);


	}
	static void twoogLog(String message) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTC§6§l]§a " + message));
	}

	static void twoogLogWarn(String message) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTC§6§l]§c " + message));
	}

}
package me.twooglz.twoogmod;


// import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;
import org.spongepowered.include.com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
// import static com.mojang.brigadier.arguments.*;
import static com.mojang.brigadier.arguments.StringArgumentType.*;


public class TwoogMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("twoogmod");

	static boolean autoTPAEnabled = false;
	static ArrayList<UUID> autoTPAWhitelistedUUIDs = new ArrayList<>();




	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution

		LOGGER.info("Loading TwoogMod");
		loadAutoTPASettings();


		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("tpa")
				.then(argument("target", word())
					.suggests((context, builder) -> {
						MinecraftClient client = MinecraftClient.getInstance();
						if (client == null || client.getNetworkHandler() == null) {
							return CompletableFuture.completedFuture(builder.build());
						}

						Collection<PlayerListEntry> players = client.getNetworkHandler().getPlayerList();
						for (PlayerListEntry player : players) {
							builder.suggest(player.getProfile().getName());
						}
						return builder.buildFuture();
					})
						.executes(context -> {
							final String target = getString(context, "target");

							return TPAToIdOrName(context);
						}
				))));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("autotpa")
				.then(literal("status")
					.then(argument("operation", word())
						.suggests((context, builder) -> {
							builder.suggest("disable");
							builder.suggest("enable");

                            return builder.buildFuture();
                        })
							.executes(context -> {
								final String operation = getString(context, "operation");
								if (operation.equals("enable")) {
									autoTPAEnabled = true;
									twoogLog("Current auto TPA state: " + autoTPAEnabled);
								} else if (operation.equals("disable")) {
									autoTPAEnabled = false;
									twoogLog("Current auto TPA state: " + autoTPAEnabled);
								}

								return 1;
							})
					)
				)
				.then(literal("whitelist")
					.then(literal("add")                                                                          // Whitelist add
						.then(argument("name", word())
							.suggests((context, builder) -> {
								MinecraftClient client = MinecraftClient.getInstance();
								if (client == null || client.getNetworkHandler() == null) {
									return CompletableFuture.completedFuture(builder.build());
								}

								Collection<PlayerListEntry> players = client.getNetworkHandler().getPlayerList();
								for (PlayerListEntry player : players) {
									builder.suggest(player.getProfile().getName());
								}
								return builder.buildFuture();
							})
								.executes(context -> {
									final String name = getString(context, "name");

									UUID uuid = getUuidFromPlayerName(name);
									addUuidToTPAWhitelist(uuid);



									saveAutoTPASettings();

									return 1;
								})
						)
					).then(literal("remove")                                                                      // Whitelist remove
						.then(argument("name", word())
							.suggests((context, builder) -> {
								MinecraftClient client = MinecraftClient.getInstance();
								if (client == null || client.getNetworkHandler() == null) {
									return CompletableFuture.completedFuture(builder.build());
								}

								Collection<PlayerListEntry> players = client.getNetworkHandler().getPlayerList();
								for (PlayerListEntry player : players) {
									builder.suggest(player.getProfile().getName());
								}
								return builder.buildFuture();
							})
								.executes(context -> {
									final String name = getString(context, "name");
									System.out.println("x");
									UUID uuid = getUuidFromPlayerName(name);
									removeUuidFromTPAWhitelist(uuid);

									saveAutoTPASettings();
									return 1;
								})
						)
					).then(literal("list")
						.executes(context -> {
							listAutoTPAWhitelist(context);
							return 1;
						})
					).then(literal("clear")
						.executes(context -> {
							autoTPAWhitelistedUUIDs.clear();
							saveAutoTPASettings();
							twoogLog("Cleared the autoTPA whitelist");
							return 1;
						})
					)
				)
			)
		);


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
	static void addUuidToTPAWhitelist(UUID uuid) {

		if (uuid != null && !autoTPAWhitelistedUUIDs.contains(uuid)) {
			autoTPAWhitelistedUUIDs.add(uuid);
			twoogLog("Added " + getStringNameFromUuid(uuid) + " to the autoTPA whitelist");
		} else {
			twoogLogError("Player doesn't exist or is already on the autoTPA whitelist!");
		}
	}

	static void removeUuidFromTPAWhitelist(UUID uuid) {

		if (uuid != null && autoTPAWhitelistedUUIDs.contains(uuid)) {
			System.out.println("UUIDin: " + uuid.toString());

			autoTPAWhitelistedUUIDs.remove(uuid);
			twoogLog("Removed " + getStringNameFromUuid(uuid) + " from the autoTPA whitelist");
		} else {
			twoogLogError("Player doesn't exist or isn't on the autoTPA whitelist!");
		}
	}

	static UUID getUuidFromPlayerName(String name) {
		UUID uuid = null;
		System.out.println(MinecraftClient.getInstance().getNetworkHandler().getPlayerList());
		for (PlayerListEntry player : MinecraftClient.getInstance().getNetworkHandler().getPlayerList()){
			if (player.getProfile().getName().equals(name)){
				uuid = player.getProfile().getId();
			}
		}

		return uuid;
	}

	static String getStringNameFromUuid(UUID uuid) {
		String name = null;

		Collection<PlayerListEntry> players = MinecraftClient.getInstance().getNetworkHandler().getPlayerList();

		for (PlayerListEntry player : players) {
			if (player.getProfile().getId().equals(uuid)) {
				name = player.getProfile().getName();
			}
		}

		return name;
	}

	static void listAutoTPAWhitelist(CommandContext<FabricClientCommandSource> context) {
		twoogLog("Whitelisted players: ");
		for (UUID whitelistedUuid : autoTPAWhitelistedUUIDs) {
			twoogLog(" - " + getStringNameFromUuid(whitelistedUuid));
		}

	}

	public static void checkForAutoTPA(String message) {

		if (!autoTPAEnabled) {return;}
		String pattern = "^(\\S+) has requested to teleport to you\\.\\s+To accept, enter or click on \\/trigger tpaccept\\.\\s+To deny, enter or click on \\/trigger tpdeny\\.$";

		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(message);

		if(!matcher.matches()) {return;}

		String username = matcher.group(1);
		if (!autoTPAWhitelistedUUIDs.contains(getUuidFromPlayerName(username))) {return;}

		MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("trigger tpaccept");

	}

	static int TPAToIdOrName(CommandContext<FabricClientCommandSource> context) {
		final String target = getString(context, "target");
		String regex = "\\d+";
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity clientPlayer = client.player;
		Scoreboard scoreboard = client.world.getScoreboard();
		ScoreboardObjective playerIdObjective = null;
		boolean isId = target.matches(regex);

		if (!isId){
			for (ScoreboardObjective current : scoreboard.getObjectives()) {

				if (current.getName().toString().equals("tpa.pid")) {
					playerIdObjective = current;
				}
			}
		}
		String target_id = null;

		if (scoreboard.getScore(ScoreHolder.fromName(target), playerIdObjective) == null && !isId){
			twoogLogError("Player does not exist!");
			return 0;
		}

		if (!isId) {

			if (playerIdObjective != null) {
				target_id = String.valueOf(scoreboard.getScore(ScoreHolder.fromName(target), playerIdObjective).getScore());
			} else {
				twoogLogError("playerIdObjective is null, tpa datapack likely not installed");
				return 0;
			}
		} else {
			target_id = target;
		}

		if (clientPlayer != null) {
			clientPlayer.networkHandler.sendChatCommand("trigger tpa set " + target_id);
		}
		return 1;
	}

	static int TPAToIdOrName(String target) {
		ServerCommandSource playerCommandSource = MinecraftClient.getInstance().player.getCommandSource();
		String regex = "\\d+";
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity clientPlayer = client.player;
		Scoreboard scoreboard = client.world.getScoreboard();
		ScoreboardObjective playerIdObjective = null;
		boolean isId = target.matches(regex);

		if (!isId){
			for (ScoreboardObjective current : scoreboard.getObjectives()) {

				if (current.getName().toString().equals("tpa.pid")) {
					playerIdObjective = current;
				}
			}
		}
		String target_id = null;

		if (scoreboard.getScore(ScoreHolder.fromName(target), playerIdObjective) == null && !isId){
			twoogLogError("Player does not exist!");
			return 0;
		}

		if (!isId) {

			if (playerIdObjective != null) {
				target_id = String.valueOf(scoreboard.getScore(ScoreHolder.fromName(target), playerIdObjective).getScore());
			} else {
				twoogLogError("playerIdObjective is null, tpa datapack likely not installed");
				return 0;
			}
		} else {
			target_id = target;
		}

		if (clientPlayer != null) {
			clientPlayer.networkHandler.sendChatCommand("trigger tpa set " + target_id);
		}
		return 1;
	}
	static void saveAutoTPASettings() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		AutoTPASettings settings = new AutoTPASettings(autoTPAEnabled, autoTPAWhitelistedUUIDs);

		Path configDir = FabricLoader.getInstance().getConfigDir();
		Path settingsFile = configDir.resolve("autotpa_settings.json");

		try (FileWriter writer = new FileWriter(settingsFile.toFile())) {
			gson.toJson(settings, writer);
		} catch (IOException e) {
			LOGGER.error("Error saving auto TPA settings to JSON", e);
		}
	}

	static class AutoTPASettings {
		boolean autoTPAEnabled;
		ArrayList<UUID> autoTPAWhitelistedUUIDs;

		public AutoTPASettings(boolean autoTPAEnabled, ArrayList<UUID> autoTPAWhitelistedUUIDs) {
			this.autoTPAEnabled = autoTPAEnabled;
			this.autoTPAWhitelistedUUIDs = autoTPAWhitelistedUUIDs;
		}
	}

	static void loadAutoTPASettings() {
		Path configDir = FabricLoader.getInstance().getConfigDir();
		Path settingsFile = configDir.resolve("autotpa_settings.json");

		try (FileReader reader = new FileReader(settingsFile.toFile())) {
			Gson gson = new Gson();
			AutoTPASettings settings = gson.fromJson(reader, AutoTPASettings.class);

			if (settings != null) {
				autoTPAEnabled = settings.autoTPAEnabled;
				autoTPAWhitelistedUUIDs = settings.autoTPAWhitelistedUUIDs;
			}
			LOGGER.info("Loaded autoTPA settings from JSON");

		} catch (IOException | JsonSyntaxException e) {
			LOGGER.error("Error loading autoTPA settings from JSON", e);
		}
	}

	static void twoogLog(String message) {
		MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTC§6§l]§a " + message));
	}

	static void twoogLogError(String message) {
		MinecraftClient.getInstance().player.sendMessage(Text.literal("§6§l[§b§lTC§6§l]§c " + message));
	}

}
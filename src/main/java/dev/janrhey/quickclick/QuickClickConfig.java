package dev.janrhey.quickclick;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tiny JSON config: config/quickclick.json
 *
 * enabled:         master on/off switch (also in the Mod Menu settings screen).
 * missDelayTicks:  pause after swinging at air and missing (vanilla 10). Clamped to 1..10.
 * blockDelayTicks: pause between breaking one block and starting the next (vanilla 5).
 *                  Clamped to 1..5.
 *
 * Delays can be reduced but never removed.
 */
public final class QuickClickConfig {
	public static final int VANILLA_MISS_DELAY = 10;
	public static final int VANILLA_BLOCK_DELAY = 5;
	public static final int MIN_DELAY = 1;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("quickclick.json");

	/** Written to / read from the JSON file. */
	public boolean enabled = true;
	public int missDelayTicks = 5;
	public int blockDelayTicks = 3;

	private static QuickClickConfig instance = new QuickClickConfig();

	private QuickClickConfig() {
	}

	public static boolean isEnabled() {
		return instance.enabled;
	}

	public static void setEnabled(boolean enabled) {
		instance.enabled = enabled;
	}

	public static int missDelayTicks() {
		return instance.missDelayTicks;
	}

	public static void setMissDelayTicks(int ticks) {
		instance.missDelayTicks = clamp(ticks, VANILLA_MISS_DELAY);
	}

	public static int blockDelayTicks() {
		return instance.blockDelayTicks;
	}

	public static void setBlockDelayTicks(int ticks) {
		instance.blockDelayTicks = clamp(ticks, VANILLA_BLOCK_DELAY);
	}

	private static int clamp(int ticks, int vanilla) {
		return Math.max(MIN_DELAY, Math.min(vanilla, ticks));
	}

	public static void load() {
		try {
			if (Files.exists(FILE)) {
				QuickClickConfig loaded = GSON.fromJson(Files.readString(FILE), QuickClickConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			}
		} catch (Exception e) {
			QuickClickClient.LOGGER.warn("Could not read {}, using defaults", FILE, e);
		}

		instance.missDelayTicks = clamp(instance.missDelayTicks, VANILLA_MISS_DELAY);
		instance.blockDelayTicks = clamp(instance.blockDelayTicks, VANILLA_BLOCK_DELAY);
		save();
	}

	public static void save() {
		try {
			Files.writeString(FILE, GSON.toJson(instance));
		} catch (IOException e) {
			QuickClickClient.LOGGER.warn("Could not write {}", FILE, e);
		}
	}
}

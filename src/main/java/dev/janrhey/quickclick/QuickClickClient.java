package dev.janrhey.quickclick;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickClickClient implements ClientModInitializer {
	public static final String MOD_ID = "quickclick";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		QuickClickConfig.load();
		LOGGER.info("Quick Click loaded: enabled={}, miss delay {} ticks (vanilla {}), block delay {} ticks (vanilla {})",
				QuickClickConfig.isEnabled(),
				QuickClickConfig.missDelayTicks(), QuickClickConfig.VANILLA_MISS_DELAY,
				QuickClickConfig.blockDelayTicks(), QuickClickConfig.VANILLA_BLOCK_DELAY);
	}
}

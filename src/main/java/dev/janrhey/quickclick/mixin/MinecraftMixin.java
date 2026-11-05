package dev.janrhey.quickclick.mixin;

import dev.janrhey.quickclick.QuickClickConfig;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Vanilla sets Minecraft#missTime to 10 when you swing at air (a miss) and blocks new
 * swings until it counts down. Each tick we lower it to the configured value (1..10).
 *
 * Values above 10 are special "do not attack" markers the game uses (for example right
 * after a menu closes), so those are left alone. The value is never set to 0 and never
 * raised. The attack cooldown the server uses to calculate damage is not touched.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	private int missTime;

	@Inject(method = "tick", at = @At("HEAD"))
	private void quickclick$shortenMissDelay(CallbackInfo ci) {
		if (!QuickClickConfig.isEnabled()) {
			return;
		}

		int target = QuickClickConfig.missDelayTicks();
		if (this.missTime > target && this.missTime <= QuickClickConfig.VANILLA_MISS_DELAY) {
			this.missTime = target;
		}
	}
}

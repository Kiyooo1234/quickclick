package dev.janrhey.quickclick.mixin;

import dev.janrhey.quickclick.QuickClickConfig;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Vanilla waits 5 ticks (MultiPlayerGameMode#destroyDelay) after you break a block before
 * you can start on the next one while holding left click. Each tick we lower it to the
 * configured value (1..5). It is never set to 0, and how long each block takes to break
 * is not changed.
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
	@Shadow
	private int destroyDelay;

	@Inject(method = "tick", at = @At("HEAD"))
	private void quickclick$shortenBlockDelay(CallbackInfo ci) {
		if (!QuickClickConfig.isEnabled()) {
			return;
		}

		int target = QuickClickConfig.blockDelayTicks();
		if (this.destroyDelay > target) {
			this.destroyDelay = target;
		}
	}
}

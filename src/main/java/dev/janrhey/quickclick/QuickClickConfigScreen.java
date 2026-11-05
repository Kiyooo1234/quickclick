package dev.janrhey.quickclick;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/** Settings screen (opened from Mod Menu): on/off, miss delay and block-break delay. */
public class QuickClickConfigScreen extends Screen {
	private final Screen parent;

	public QuickClickConfigScreen(Screen parent) {
		super(Component.literal("Quick Click"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int top = this.height / 2 - 40;

		this.addRenderableWidget(Button.builder(toggleText(), button -> {
			QuickClickConfig.setEnabled(!QuickClickConfig.isEnabled());
			button.setMessage(toggleText());
		}).bounds(cx - 100, top, 200, 20).build());

		this.addRenderableWidget(Button.builder(missText(), button -> {
			QuickClickConfig.setMissDelayTicks(nextLower(QuickClickConfig.missDelayTicks(),
					QuickClickConfig.VANILLA_MISS_DELAY));
			button.setMessage(missText());
		}).bounds(cx - 100, top + 24, 200, 20).build());

		this.addRenderableWidget(Button.builder(blockText(), button -> {
			QuickClickConfig.setBlockDelayTicks(nextLower(QuickClickConfig.blockDelayTicks(),
					QuickClickConfig.VANILLA_BLOCK_DELAY));
			button.setMessage(blockText());
		}).bounds(cx - 100, top + 48, 200, 20).build());

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
				.bounds(cx - 100, top + 80, 200, 20).build());
	}

	/** One tick lower than the current value, wrapping back to vanilla after the minimum. */
	private static int nextLower(int current, int vanilla) {
		int next = current - 1;
		return next < QuickClickConfig.MIN_DELAY ? vanilla : next;
	}

	private static Component toggleText() {
		return Component.literal("Quick Click: " + (QuickClickConfig.isEnabled() ? "ON" : "OFF"));
	}

	private static Component missText() {
		return Component.literal("Swing-miss delay: " + QuickClickConfig.missDelayTicks()
				+ " ticks (vanilla " + QuickClickConfig.VANILLA_MISS_DELAY + ")");
	}

	private static Component blockText() {
		return Component.literal("Block-break delay: " + QuickClickConfig.blockDelayTicks()
				+ " ticks (vanilla " + QuickClickConfig.VANILLA_BLOCK_DELAY + ")");
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
	}

	@Override
	public void onClose() {
		QuickClickConfig.save();
		this.minecraft.setScreen(this.parent);
	}
}

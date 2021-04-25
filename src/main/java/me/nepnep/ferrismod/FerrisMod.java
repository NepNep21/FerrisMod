package me.nepnep.ferrismod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Date;

public class FerrisMod implements ClientModInitializer {

	private static KeyBinding keyBinding;
	private static boolean shouldRender = false;

	@Override
	public void onInitializeClient() {

		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.ferrismod.toggle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_END,
				"category.ferrismod"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keyBinding.wasPressed()) {
				shouldRender = !shouldRender;
			}
		});

		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (shouldRender) {
				MinecraftClient client = MinecraftClient.getInstance();
				if (new Date().getTime() / 1000 % 2 == 0) {
					client.getTextureManager().bindTexture(new Identifier("ferrismod", "textures/pop_ferris_open.png"));
					DrawableHelper.drawTexture(matrixStack, 0, 0, 0, 0, 100, 100, 100, 100);
				} else {
					client.getTextureManager().bindTexture(new Identifier("ferrismod", "textures/pop_ferris_closed.png"));
					DrawableHelper.drawTexture(matrixStack, 0, 0, 0, 0, 100, 100, 100, 100);
				}
			}
		});
	}
}

package me.MiniDigger.Foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.command.CommandHandler;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;
import me.MiniDigger.Foundation.handler.lang.Lang;
import me.MiniDigger.Foundation.handler.lang.LangHandler;
import me.MiniDigger.Foundation.handler.lang.LangKey;
import me.MiniDigger.Foundation.handler.module.ModuleHandler;

public class FoundationMain extends JavaPlugin {
	private static FoundationMain INSTANCE;
	private static boolean testMode;
	private static final FoundationHandler[] handler = { LangHandler.getInstance(), CommandHandler.getInstance(),
			ConfigHandler.getInstance(), ModuleHandler.getInstance() };

	@Override
	public void onLoad() {
		INSTANCE = this;
	}

	@Override
	public void onEnable() {
		Lang.console(LangKey.Foundation.ENABLE, getDescription().getVersion());
		initHandler();
	}

	@Override
	public void onDisable() {
		disableHandler();
	}

	private void initHandler() {
		for (FoundationHandler h : handler) {
			h.onLoad();
		}
		for (FoundationHandler h : handler) {
			h.onEnable();
		}
	}

	private void disableHandler() {
		for (FoundationHandler h : handler) {
			h.onDisable();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return super.onCommand(sender, command, label, args);
	}

	public static FoundationMain getInstance() {
		return INSTANCE;
	}

	public static boolean isInTestMode() {
		return testMode;
	}

	public static void setTestMode(boolean testMode) {
		FoundationMain.testMode = testMode;
	}

	public static CommandSender getTestCommandSender() {
		return new TestCommandSender();
	}
}

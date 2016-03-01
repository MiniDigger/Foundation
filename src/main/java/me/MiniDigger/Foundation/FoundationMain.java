package me.MiniDigger.Foundation;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.command.CommandHandler;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;
import me.MiniDigger.Foundation.handler.lang.*;
import me.MiniDigger.Foundation.handler.module.ModuleHandler;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class FoundationMain extends JavaPlugin {
	private static FoundationMain INSTANCE;
	private static boolean testMode;
	private static final FoundationHandler[] handler = { LangHandler.getInstance(), CommandHandler.getInstance(), ConfigHandler.getInstance(),
			ModuleHandler.getInstance() };
			
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
		for (final FoundationHandler h : handler) {
			h.onLoad();
		}
		for (final FoundationHandler h : handler) {
			h.onEnable();
		}
	}
	
	private void disableHandler() {
		for (final FoundationHandler h : handler) {
			h.onDisable();
		}
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		return super.onCommand(sender, command, label, args);
	}
	
	public static FoundationMain getInstance() {
		return INSTANCE;
	}
	
	public static boolean isInTestMode() {
		return testMode;
	}
	
	public static void setTestMode(final boolean testMode) {
		FoundationMain.testMode = testMode;
	}
	
	public static CommandSender getTestCommandSender() {
		return new TestCommandSender();
	}
}

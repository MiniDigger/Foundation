package me.MiniDigger.Foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.MiniDigger.Foundation.handler.lang.Lang;
import me.MiniDigger.Foundation.handler.lang.LangKey;

public class FoundationMain extends JavaPlugin {
	private static FoundationMain INSTANCE;
	private static boolean testMode;

	@Override
	public void onLoad() {
		INSTANCE = this;
	}

	@Override
	public void onEnable() {
		Lang.console(LangKey.Foundation.ENABLE, getDescription().getVersion());
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
	
	public static CommandSender getTestCommandSender(){
		return new TestCommandSender();
	}
}

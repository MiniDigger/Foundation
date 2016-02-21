package me.MiniDigger.Foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.MiniDigger.Foundation.handler.FoundationHandler;

public class FoundationMain extends JavaPlugin {
	private static FoundationMain INSTANCE;

	@Override
	public void onLoad() {
		INSTANCE = this;
	}

	@Override
	public void onEnable() {
		System.out.println("Lets get this started!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return super.onCommand(sender, command, label, args);
	}
	
	public static FoundationMain getInstance() {
		return INSTANCE;
	}
}

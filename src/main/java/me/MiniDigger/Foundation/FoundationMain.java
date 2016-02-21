package me.MiniDigger.Foundation;

import org.bukkit.plugin.java.JavaPlugin;

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

	public static FoundationMain getInstance() {
		return INSTANCE;
	}
}

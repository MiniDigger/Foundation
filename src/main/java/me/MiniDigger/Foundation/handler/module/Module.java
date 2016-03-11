package me.MiniDigger.Foundation.handler.module;

import java.io.File;

import me.MiniDigger.Foundation.FoundationMain;

public class Module {

	private ModuleDescription description;

	public boolean onLoad() {
		return true;
	}

	public boolean onEnable() {
		return true;
	}

	public boolean onDisable() {
		return true;
	}

	public ModuleDescription getDescription() {
		return description;
	}

	public void setDescription(final ModuleDescription description) {
		this.description = description;
	}

	public File getDataFolder() {
		return new File(FoundationMain.getInstance().getDataFolder(), description.name());
	}
}

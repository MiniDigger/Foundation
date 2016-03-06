package me.MiniDigger.Foundation.handler.module;

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
	
	public void setDescription(ModuleDescription description) {
		this.description = description;
	}
}

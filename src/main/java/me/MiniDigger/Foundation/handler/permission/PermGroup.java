package me.MiniDigger.Foundation.handler.permission;

public class PermGroup extends PermHolder {
	private String name;

	public PermGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

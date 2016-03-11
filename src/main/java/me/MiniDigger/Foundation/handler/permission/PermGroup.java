package me.MiniDigger.Foundation.handler.permission;

public class PermGroup extends PermHolder {
	private final String name;

	public PermGroup(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

package me.MiniDigger.Foundation.handler.command;

public abstract class CommandAdapter {

	protected Object o;

	public Object value() {
		return o;
	}

	public abstract String consome(String consume, int index) throws CommandArgsNotMatchedException;

	public abstract boolean matches(Class<?> c);

}

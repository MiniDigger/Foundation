package me.MiniDigger.Foundation.handler.config;

public abstract class ConfigAdapter {
	
	public abstract Class<?> getClazz();

	public abstract Object fromString(String source);

	public abstract String toString(Object source);
}

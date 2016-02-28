package me.MiniDigger.Foundation.handler.config;

public class ConfigAdapterNotApplicable extends RuntimeException {

	private static final long serialVersionUID = -6123858214743380281L;

	public ConfigAdapterNotApplicable(ConfigAdapter a, Object source) {
		super("The config adapter " + a.getClass().getSimpleName() + " is not applicable for an object of type "
				+ source.getClass().getSimpleName());
	}

	public ConfigAdapterNotApplicable(ConfigAdapter a, String source) {
		super("The config adapter " + a.getClass().getSimpleName() + " is not applicable for source " + source);
	}
}

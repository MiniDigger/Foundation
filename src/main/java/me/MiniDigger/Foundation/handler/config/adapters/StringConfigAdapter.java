package me.MiniDigger.Foundation.handler.config.adapters;

import me.MiniDigger.Foundation.handler.config.ConfigAdapter;
import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;

public class StringConfigAdapter extends ConfigAdapter {

	@Override
	public Object fromString(final String source) {
		return source;
	}

	@Override
	public String toString(final Object source) {
		if (source instanceof String) {
			return (String) source;
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

	@Override
	public Class<?> getClazz() {
		return String.class;
	}
}

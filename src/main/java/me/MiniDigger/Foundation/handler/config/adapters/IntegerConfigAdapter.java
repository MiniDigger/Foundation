package me.MiniDigger.Foundation.handler.config.adapters;

import me.MiniDigger.Foundation.handler.config.ConfigAdapter;
import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;

public class IntegerConfigAdapter extends ConfigAdapter {

	@Override
	public Object fromString(String source) {
		try {
			return Integer.parseInt(source);
		} catch (NumberFormatException ex) {
			throw new ConfigAdapterNotApplicable(this, source);
		}
	}

	@Override
	public String toString(Object source) {
		if (source instanceof Integer) {
			return ((Integer) source).intValue() + "";
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

	@Override
	public Class<?> getClazz() {
		return Integer.class;
	}

}

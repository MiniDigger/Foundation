package me.MiniDigger.Foundation.handler.config.adapters;

import org.apache.commons.lang3.ArrayUtils;

import me.MiniDigger.Foundation.handler.config.ConfigAdapter;
import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;

public class BooleanConfigAdapter extends ConfigAdapter {

	protected static final String[] trueStrings = { "true", "yes", "yeah", "y", "t", "ja" };
	protected static final String[] falseStrings = { "false", "no", "nope", "n", "f", "nein" };

	@Override
	public Object fromString(final String source) {
		if (ArrayUtils.contains(trueStrings, source.toLowerCase())) {
			return Boolean.TRUE;
		} else if (ArrayUtils.contains(falseStrings, source.toLowerCase())) {
			return Boolean.FALSE;
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

	@Override
	public String toString(final Object source) {
		if (source instanceof Boolean) {
			final Boolean b = (Boolean) source;
			if (b) {
				return "true";
			} else {
				return "false";
			}
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

	@Override
	public Class<?> getClazz() {
		return Boolean.class;
	}

}

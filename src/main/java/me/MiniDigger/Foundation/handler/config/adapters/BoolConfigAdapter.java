package me.MiniDigger.Foundation.handler.config.adapters;

import org.apache.commons.lang3.ArrayUtils;

import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;

public class BoolConfigAdapter extends BooleanConfigAdapter {
	@Override
	public Class<?> getClazz() {
		return boolean.class;
	}

	@Override
	public Object fromString(final String source) {
		if (ArrayUtils.contains(trueStrings, source.toLowerCase())) {
			return true;
		} else if (ArrayUtils.contains(falseStrings, source.toLowerCase())) {
			return true;
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}
}

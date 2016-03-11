package me.MiniDigger.Foundation.handler.command.adapters;

import org.apache.commons.lang3.ArrayUtils;

import me.MiniDigger.Foundation.handler.command.CommandAdapter;
import me.MiniDigger.Foundation.handler.command.CommandArgsNotMatchedException;

public class BooleanAdapter extends CommandAdapter {

	public static final String[] trueStrings = { "true", "yes", "yeah", "y", "t", "ja" };
	public static final String[] falseStrings = { "false", "no", "nope", "n", "f", "nein" };

	@Override
	public String consome(final String consume, final int index) throws CommandArgsNotMatchedException {
		final String val = consume.split(" ")[0];
		if (val == null || val.length() == 0) {
			throw new CommandArgsNotMatchedException(index, "Boolean", consume);
		}
		if (ArrayUtils.contains(trueStrings, val)) {
			o = true;
		} else if (ArrayUtils.contains(falseStrings, val)) {
			o = false;
		} else {
			throw new CommandArgsNotMatchedException(index, "Boolean", val);
		}
		return consume.replaceFirst(val, "");

	}

	@Override
	public boolean matches(final Class<?> c) {
		return Boolean.class.equals(c);
	}

}

package me.MiniDigger.Foundation.handler.command.adapters;

import me.MiniDigger.Foundation.handler.command.CommandAdapter;
import me.MiniDigger.Foundation.handler.command.CommandArgsNotMatchedException;

public class StringAdapter extends CommandAdapter {

	@Override
	public String consome(final String consume, final int index) throws CommandArgsNotMatchedException {
		String val = "";
		if (consume.startsWith("\"")) {
			val = consume.substring(1, consume.indexOf("\"", 1));
		} else {
			val = consume.split(" ")[0];
		}
		if (val == null || val.length() == 0) {
			throw new CommandArgsNotMatchedException(index, "String", consume);
		}
		o = val;
		return consume.replaceFirst(val, "");
	}

	@Override
	public boolean matches(final Class<?> c) {
		return c.equals(String.class);
	}

}

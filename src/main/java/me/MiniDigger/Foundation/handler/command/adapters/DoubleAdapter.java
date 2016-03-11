package me.MiniDigger.Foundation.handler.command.adapters;

import me.MiniDigger.Foundation.handler.command.CommandAdapter;
import me.MiniDigger.Foundation.handler.command.CommandArgsNotMatchedException;

public class DoubleAdapter extends CommandAdapter {

	@Override
	public String consome(String consume, final int index) throws CommandArgsNotMatchedException {
		double d = 0;
		try {
			d = Double.parseDouble(consume.split(" ")[0]);
			o = d;
		} catch (final NumberFormatException e) {
			if (consume.contains(",")) {
				consume += " (only '.' allowed!)";
			}
			throw new CommandArgsNotMatchedException(index, "Double", consume);
		}
		return consume.replaceFirst(d + "", "");
	}

	@Override
	public boolean matches(final Class<?> c) {
		return c.equals(Double.class);
	}
}

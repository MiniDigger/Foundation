package me.MiniDigger.Foundation.handler.command.adapters;

import me.MiniDigger.Foundation.handler.command.CommandAdapter;
import me.MiniDigger.Foundation.handler.command.CommandArgsNotMatchedException;

public class IntegerAdapter extends CommandAdapter {

	@Override
	public String consome(String consume, int index) throws CommandArgsNotMatchedException {
		int i = 0;
		try {
			i = Integer.parseInt(consume.split(" ")[0]);
			o = i;
		} catch (NumberFormatException e) {
			throw new CommandArgsNotMatchedException(index, "Integer", consume + " (is integer to long?)");
		}
		return consume.replaceFirst(i + "", "");
	}

	@Override
	public boolean matches(Class<?> c) {
		return c.equals(Integer.class);
	}

}

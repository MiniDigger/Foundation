package me.MiniDigger.Foundation.handler.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

public class Command {

	private String name;
	private boolean console;
	private String usage;
	private String permission;
	private String description;
	private Method method;
	private Object obj;

	private final List<CommandAdapter> adapter = new ArrayList<>();

	public void setName(final String name) {
		this.name = name;
	}

	public void setConsole(final boolean console) {
		this.console = console;
	}

	public void setUsage(final String usage) {
		this.usage = usage;
	}

	public void setPermission(final String permission) {
		this.permission = permission;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setMethod(final Method m) {
		method = m;
	}

	public void setObject(final Object obj) {
		this.obj = obj;
	}

	public String getName() {
		return name;
	}

	public boolean isConsole() {
		return console;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}

	public String getDescription() {
		return description;
	}

	public Method getMethod() {
		return method;
	}

	public Object getObj() {
		return obj;
	}

	public void execute(final CommandSender sender, String input) {
		// get rid of the cmd name
		final String[] n = name.split(Pattern.quote("."));
		final StringBuilder b = new StringBuilder();
		for (final String s : n) {
			b.append(s);
			b.append(" ");
		}
		input = input.replaceFirst(b.toString(), "");

		String consume = input;
		final List<Object> obj = new ArrayList<>();
		obj.add(sender);
		for (int i = 0; i < adapter.size(); i++) {
			final CommandAdapter a = adapter.get(i);
			consume = consume.trim();
			try {
				consume = a.consome(consume, i);
			} catch (final CommandArgsNotMatchedException e) {
				sender.sendMessage("Wrong arguments: " + e.getMessage());
				return;
			}
			obj.add(a.value());
		}

		try {
			method.invoke(this.obj, obj.toArray());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void register(final List<CommandAdapter> registeredAdapters) throws CommandWrongParamsException {
		boolean foundSender = false;
		for (final Class<?> c : method.getParameterTypes()) {
			if (c.equals(CommandSender.class)) {
				foundSender = true;
				continue;
			}

			final CommandAdapter a = getAdapter(c, registeredAdapters);
			if (a == null) {
				throw new CommandWrongParamsException("No config adaper found for param " + c.getCanonicalName() + "!");
			}
			adapter.add(a);
		}

		if (!foundSender) {
			throw new CommandWrongParamsException("No sender param found!");
		}
	}

	private CommandAdapter getAdapter(final Class<?> c, final List<CommandAdapter> registeredAdapters) {
		for (final CommandAdapter a : registeredAdapters) {
			if (a.matches(c)) {
				return a;
			}
		}
		return null;
	}
}

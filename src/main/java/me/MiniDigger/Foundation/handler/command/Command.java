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

	private List<CommandAdapter> adapter = new ArrayList<>();

	public void setName(String name) {
		this.name = name;
	}

	public void setConsole(boolean console) {
		this.console = console;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMethod(Method m) {
		this.method = m;
	}

	public void setObject(Object obj) {
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

	public void execute(CommandSender sender, String input) {
		// get rid of the cmd name
		String[] n = name.split(Pattern.quote("."));
		StringBuilder b = new StringBuilder();
		for (String s : n) {
			b.append(s);
			b.append(" ");
		}
		input = input.replaceFirst(b.toString(), "");

		String consume = input;
		List<Object> obj = new ArrayList<>();
		obj.add(sender);
		for (int i = 0; i < adapter.size(); i++) {
			CommandAdapter a = adapter.get(i);
			consume = consume.trim();
			try {
				consume = a.consome(consume, i);
			} catch (CommandArgsNotMatchedException e) {
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

	public void register(List<CommandAdapter> registeredAdapters) throws CommandWrongParamsException {
		boolean foundSender = false;
		for (Class<?> c : method.getParameterTypes()) {
			if (c.equals(CommandSender.class)) {
				foundSender = true;
				continue;
			}

			CommandAdapter a = getAdapter(c, registeredAdapters);
			if (a == null) {
				throw new CommandWrongParamsException("No config adaper found for param " + c.getCanonicalName() + "!");
			}
			adapter.add(a);
		}

		if (!foundSender) {
			throw new CommandWrongParamsException("No sender param found!");
		}
	}

	private CommandAdapter getAdapter(Class<?> c, List<CommandAdapter> registeredAdapters) {
		for (CommandAdapter a : registeredAdapters) {
			if (a.matches(c)) {
				return a;
			}
		}
		return null;
	}
}

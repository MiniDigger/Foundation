package me.MiniDigger.Foundation.handler.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.command.adapters.BooleanAdapter;
import me.MiniDigger.Foundation.handler.command.adapters.DoubleAdapter;
import me.MiniDigger.Foundation.handler.command.adapters.IntegerAdapter;
import me.MiniDigger.Foundation.handler.command.adapters.StringAdapter;

import org.bukkit.command.CommandSender;

public class CommandHandler extends FoundationHandler {
	private final List<Command> commands = new ArrayList<>();
	private final List<CommandAdapter> adapter = new ArrayList<>();

	private static CommandHandler INSTANCE;

	@Override
	public boolean onEnable() {
		register(new StringAdapter());
		register(new IntegerAdapter());
		register(new DoubleAdapter());
		register(new BooleanAdapter());
		return super.onEnable();
	}

	@Override
	public boolean onDisable() {
		commands.clear();
		adapter.clear();
		return super.onDisable();
	}

	public void register(final CommandAdapter adp) {
		adapter.add(adp);
	}

	public void register(final Object obj) throws CommandWrongParamsException {
		for (final Method m : obj.getClass().getMethods()) {
			if (m.isAnnotationPresent(CommandDescription.class)) {
				register(m, m.getAnnotation(CommandDescription.class), obj);
			}
		}
	}

	public void unregister(final Object obj) {
		for (final Method m : obj.getClass().getMethods()) {
			if (m.isAnnotationPresent(CommandDescription.class)) {
				unregister(m.getAnnotation(CommandDescription.class));
			}
		}
	}

	public void unregister(final CommandDescription desc) {
		commands.remove(getCommand(desc.name()));
	}

	public void register(final Method m, final CommandDescription desc, final Object obj) throws CommandWrongParamsException {
		final Command command = new Command();
		command.setName(desc.name());
		command.setConsole(desc.console());
		command.setUsage(desc.usage());
		command.setPermission(desc.permission());
		command.setDescription(desc.description());
		command.setMethod(m);
		command.setObject(obj);
		command.register(adapter);
		commands.add(command);
	}

	// TODO Do something with the descripton of commands (helpmap)

	public void execute(final CommandSender sender, final String input) {
		final String[] args = input.split(" ");
		for (int i = args.length; i > 0; i--) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(args[0]);
			for (int x = 1; x < i; x++) {
				buffer.append("." + args[x].toLowerCase());
			}
			final String cmd = buffer.toString();
			final Command command = getCommand(cmd);
			if (command == null) {
				continue;
			}

			if (sender.hasPermission(command.getPermission())) {
				command.execute(sender, input);
			} else {
				sender.sendMessage("No perm to perform that action");
			}

			return;
		}
		sender.sendMessage("not handled! " + input);
	}

	private Command getCommand(final String cmd) {
		for (final Command c : commands) {
			if (c.getName().equalsIgnoreCase(cmd)) {
				return c;
			}
		}
		return null;
	}

	public static CommandHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CommandHandler();
		}
		return INSTANCE;
	}
}

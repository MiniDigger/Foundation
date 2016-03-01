package me.MiniDigger.Foundation.handler.command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.SimplePluginManager;

import me.MiniDigger.Foundation.FoundationMain;
import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.lang.Lang;
import me.MiniDigger.Foundation.handler.lang.LangKey;

/**
 * Command Framework - CommandFramework <br>
 * The main command framework class used for controlling the framework.
 *
 * @author minnymin3, modified by MiniDigger
 *
 */
public class CommandHandler extends FoundationHandler implements CommandExecutor {

	private static CommandHandler INSTANCE;

	private final Map<String, Entry<Method, Object>> commandMap = new HashMap<String, Entry<Method, Object>>();
	private CommandMap map = new SimpleCommandMap(null);
	private Plugin plugin;
	// TODO Add a config option for relocations
	private final Map<String, String> relocations = new HashMap<>();

	@Override
	public boolean onEnable() {
		plugin = FoundationMain.getInstance();
		if (plugin == null) {
			return true; // we are in a unit test
		}

		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
			try {
				final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap) field.get(manager);

				final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field2.setAccessible(true);
				final Map<String, org.bukkit.command.Command> newknownCommands = new HashMap<>();
				@SuppressWarnings("unchecked")
				final Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2.get(map);
				for (final Map.Entry<String, org.bukkit.command.Command> entry : knownCommands.entrySet()) {
					for (final String key : relocations.keySet()) {
						if (entry.getKey().startsWith(key)) {
							newknownCommands.put(entry.getKey().replaceFirst(key, relocations.get(key)), entry.getValue());
							Lang.console(LangKey.Command.RELOCATION, entry.getKey(), entry.getKey().replaceFirst(key, relocations.get(key)));
						} else {
							entry.getValue().unregister(map);
						}
					}
				}
				knownCommands.clear();
				knownCommands.putAll(newknownCommands);
			} catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) {
				Lang.error(e);
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
		return handleCommand(sender, cmd, label, args);
	}

	/**
	 * Handles commands. Used in the onCommand method in your JavaPlugin class
	 *
	 * @param sender
	 *            The {@link org.bukkit.command.CommandSender} parsed from
	 *            onCommand
	 * @param cmd
	 *            The {@link org.bukkit.command.Command} parsed from onCommand
	 * @param label
	 *            The label parsed from onCommand
	 * @param args
	 *            The arguments parsed from onCommand
	 * @return Always returns true for simplicity's sake in onCommand
	 */
	public boolean handleCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
		for (int i = args.length; i >= 0; i--) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(label.toLowerCase());
			for (int x = 0; x < i; x++) {
				buffer.append("." + args[x].toLowerCase());
			}
			final String cmdLabel = buffer.toString();
			if (commandMap.containsKey(cmdLabel)) {
				final Method method = commandMap.get(cmdLabel).getKey();
				final Object methodObject = commandMap.get(cmdLabel).getValue();
				final Command command = method.getAnnotation(Command.class);
				if (command.permission() != "" && !sender.hasPermission(command.permission())) {
					// TODO find a better way to do this, we need translating!
					sender.sendMessage(command.noPerm());
					return true;
				}
				if (command.inGameOnly() && !(sender instanceof Player)) {
					Lang.msg(sender, LangKey.Command.ONLY_INGAME);
					return true;
				}
				try {
					method.invoke(methodObject, new CommandArgs(sender, cmd, label, args, cmdLabel.split("\\.").length - 1));
				} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
					Lang.error(e);
				}
				return true;
			}
		}
		defaultCommand(new CommandArgs(sender, cmd, label, args, 0));
		return true;
	}

	/**
	 * Registers all command and completer methods inside of the object. Similar
	 * to Bukkit's registerEvents method.
	 *
	 * @param obj
	 *            The object to register the commands of
	 */
	public void registerCommands(final Object obj) {
		for (final Method m : obj.getClass().getMethods()) {
			if (m.getAnnotation(Command.class) != null) {
				final Command command = m.getAnnotation(Command.class);
				if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
					Lang.console(LangKey.Command.COMMAND_UNEXPECTED_METHOD_ARGS, m.getName());
					continue;
				}
				registerCommand(command, command.name(), m, obj);
				for (final String alias : command.aliases()) {
					registerCommand(command, alias, m, obj);
				}
			} else if (m.getAnnotation(Completer.class) != null) {
				final Completer comp = m.getAnnotation(Completer.class);
				if (m.getParameterTypes().length > 1 || m.getParameterTypes().length == 0 || m.getParameterTypes()[0] != CommandArgs.class) {
					Lang.console(LangKey.Command.COMPLETER_UNEXPECTED_METHOD_ARGS, m.getName());
					continue;
				}
				if (m.getReturnType() != List.class) {
					Lang.console(LangKey.Command.COMPLETER_UNEXPECTED_RETURN_TYPE, m.getName());
					continue;
				}
				registerCompleter(comp.name(), m, obj);
				for (final String alias : comp.aliases()) {
					registerCompleter(alias, m, obj);
				}
			}
		}
	}

	/**
	 * Registers all the commands under the plugin's help
	 */
	public void registerHelp() {
		final Set<HelpTopic> help = new TreeSet<HelpTopic>(HelpTopicComparator.helpTopicComparatorInstance());
		for (final String s : commandMap.keySet()) {
			if (!s.contains(".")) {
				final org.bukkit.command.Command cmd = map.getCommand(s);
				final HelpTopic topic = new GenericCommandHelpTopic(cmd);
				help.add(topic);
			}
		}
		final IndexHelpTopic topic = new IndexHelpTopic(plugin.getName(), Lang.translate(LangKey.Command.HELP_TITLE, plugin.getName()), null, help,
				Lang.translate(LangKey.Command.HELP_BODY, plugin.getName()));
		Bukkit.getServer().getHelpMap().addTopic(topic);
	}

	public void registerCommand(final Command command, final String label, final Method m, final Object obj) {
		commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
		commandMap.put(plugin.getName() + ':' + label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
		final String cmdLabel = label.split("\\.")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null) {
			final org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, this, plugin);
			map.register(plugin.getName(), cmd);
		}
		if (!command.description().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setDescription(command.description());
		}
		if (!command.usage().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setUsage(command.usage());
		}
	}

	public void registerCompleter(final String label, final Method m, final Object obj) {
		final String cmdLabel = label.split("\\.")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null) {
			final org.bukkit.command.Command command = new BukkitCommand(cmdLabel, this, plugin);
			map.register(plugin.getName(), command);
		}
		if (map.getCommand(cmdLabel) instanceof BukkitCommand) {
			final BukkitCommand command = (BukkitCommand) map.getCommand(cmdLabel);
			if (command.completer == null) {
				command.completer = new BukkitCompleter();
			}
			command.completer.addCompleter(label, m, obj);
		} else if (map.getCommand(cmdLabel) instanceof PluginCommand) {
			try {
				final Object command = map.getCommand(cmdLabel);
				final Field field = command.getClass().getDeclaredField("completer");
				field.setAccessible(true);
				if (field.get(command) == null) {
					final BukkitCompleter completer = new BukkitCompleter();
					completer.addCompleter(label, m, obj);
					field.set(command, completer);
				} else if (field.get(command) instanceof BukkitCompleter) {
					final BukkitCompleter completer = (BukkitCompleter) field.get(command);
					completer.addCompleter(label, m, obj);
				} else {
					Lang.console(LangKey.Command.COMPLETER_ALREADY_REGISTERED, m.getName());
				}
			} catch (final Exception ex) {
				Lang.error(ex);
			}
		}
	}

	private void defaultCommand(final CommandArgs args) {
		Lang.msg(args.getSender(), LangKey.Command.COMMAND_NOT_HANDLED, args.getLabel());
	}

	public void unregister(final String command) {
		if (plugin.getServer() != null && plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
			try {
				final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap) field.get(manager);
				final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field2.setAccessible(true);
				@SuppressWarnings("unchecked")
				final Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2.get(map);
				for (final Map.Entry<String, org.bukkit.command.Command> entry : knownCommands.entrySet()) {
					if (entry.getKey().equals(command)) {
						entry.getValue().unregister(map);
					}
				}
				knownCommands.remove(command);
			} catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
				Lang.error(e);
			}
		}
		commandMap.remove(command);
	}

	public void unregisterCommands(final Object obj) {
		for (final Method m : obj.getClass().getMethods()) {
			if (m.getAnnotation(Command.class) != null) {
				final Command command = m.getAnnotation(Command.class);
				if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
					Lang.console(LangKey.Command.COMMAND_UNREGISTER, m.getName());
					continue;
				}
				unregister(command.name());
				for (final String alias : command.aliases()) {
					unregister(alias);
				}
			}
		}
	}

	public static CommandHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CommandHandler();
		}
		return INSTANCE;
	}

	/**
	 * JUNIT TEST
	 *
	 * @param plugin2
	 */
	public void setPlugin(PluginBase plugin2) {
		this.plugin = plugin2;
	}
}

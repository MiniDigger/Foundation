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

	private Map<String, Entry<Method, Object>> commandMap = new HashMap<String, Entry<Method, Object>>();
	private CommandMap map;
	private Plugin plugin;
	// TODO Add a config option for relocations
	private Map<String, String> relocations = new HashMap<>();

	@Override
	public boolean onEnable() {
		this.plugin = FoundationMain.getInstance();
		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
			try {
				Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap) field.get(manager);

				final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field2.setAccessible(true);
				final Map<String, org.bukkit.command.Command> newknownCommands = new HashMap<>();
				@SuppressWarnings("unchecked")
				final Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2
						.get(map);
				for (final Map.Entry<String, org.bukkit.command.Command> entry : knownCommands.entrySet()) {
					for (final String key : relocations.keySet()) {
						if (entry.getKey().startsWith(key)) {
							newknownCommands.put(entry.getKey().replaceFirst(key, relocations.get(key)),
									entry.getValue());
							Lang.console(LangKey.Command.RELOCATION, entry.getKey(),
									entry.getKey().replaceFirst(key, relocations.get(key)));
						} else {
							entry.getValue().unregister(map);
						}
					}
				}
				knownCommands.clear();
				knownCommands.putAll(newknownCommands);
			} catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) {
				Lang.error(e);
			}
		}
		return super.onEnable();
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
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
	public boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		for (int i = args.length; i >= 0; i--) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(label.toLowerCase());
			for (int x = 0; x < i; x++) {
				buffer.append("." + args[x].toLowerCase());
			}
			String cmdLabel = buffer.toString();
			if (commandMap.containsKey(cmdLabel)) {
				Method method = commandMap.get(cmdLabel).getKey();
				Object methodObject = commandMap.get(cmdLabel).getValue();
				Command command = method.getAnnotation(Command.class);
				if (command.permission() != "" && !sender.hasPermission(command.permission())) {
					// TODO find a better way to do this
					sender.sendMessage(command.noPerm());
					return true;
				}
				if (command.inGameOnly() && !(sender instanceof Player)) {
					Lang.msg(sender, LangKey.Command.ONLY_INGAME);
					return true;
				}
				try {
					method.invoke(methodObject,
							new CommandArgs(sender, cmd, label, args, cmdLabel.split("\\.").length - 1));
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
	public void registerCommands(Object obj) {
		for (Method m : obj.getClass().getMethods()) {
			if (m.getAnnotation(Command.class) != null) {
				Command command = m.getAnnotation(Command.class);
				if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
					Lang.console(LangKey.Command.COMMAND_UNEXPECTED_METHOD_ARGS, m.getName());
					continue;
				}
				registerCommand(command, command.name(), m, obj);
				for (String alias : command.aliases()) {
					registerCommand(command, alias, m, obj);
				}
			} else if (m.getAnnotation(Completer.class) != null) {
				Completer comp = m.getAnnotation(Completer.class);
				if (m.getParameterTypes().length > 1 || m.getParameterTypes().length == 0
						|| m.getParameterTypes()[0] != CommandArgs.class) {
					Lang.console(LangKey.Command.COMPLETER_UNEXPECTED_METHOD_ARGS, m.getName());
					continue;
				}
				if (m.getReturnType() != List.class) {
					Lang.console(LangKey.Command.COMPLETER_UNEXPECTED_RETURN_TYPE, m.getName());
					continue;
				}
				registerCompleter(comp.name(), m, obj);
				for (String alias : comp.aliases()) {
					registerCompleter(alias, m, obj);
				}
			}
		}
	}

	/**
	 * Registers all the commands under the plugin's help
	 */
	public void registerHelp() {
		Set<HelpTopic> help = new TreeSet<HelpTopic>(HelpTopicComparator.helpTopicComparatorInstance());
		for (String s : commandMap.keySet()) {
			if (!s.contains(".")) {
				org.bukkit.command.Command cmd = map.getCommand(s);
				HelpTopic topic = new GenericCommandHelpTopic(cmd);
				help.add(topic);
			}
		}
		IndexHelpTopic topic = new IndexHelpTopic(plugin.getName(),
				Lang.translate(LangKey.Command.HELP_TITLE, plugin.getName()), null, help,
				Lang.translate(LangKey.Command.HELP_BODY, plugin.getName()));
		Bukkit.getServer().getHelpMap().addTopic(topic);
	}

	public void registerCommand(Command command, String label, Method m, Object obj) {
		commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
		commandMap.put(this.plugin.getName() + ':' + label.toLowerCase(),
				new AbstractMap.SimpleEntry<Method, Object>(m, obj));
		String cmdLabel = label.split("\\.")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null) {
			org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, this, plugin);
			map.register(plugin.getName(), cmd);
		}
		if (!command.description().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setDescription(command.description());
		}
		if (!command.usage().equalsIgnoreCase("") && cmdLabel == label) {
			map.getCommand(cmdLabel).setUsage(command.usage());
		}
	}

	public void registerCompleter(String label, Method m, Object obj) {
		String cmdLabel = label.split("\\.")[0].toLowerCase();
		if (map.getCommand(cmdLabel) == null) {
			org.bukkit.command.Command command = new BukkitCommand(cmdLabel, this, plugin);
			map.register(plugin.getName(), command);
		}
		if (map.getCommand(cmdLabel) instanceof BukkitCommand) {
			BukkitCommand command = (BukkitCommand) map.getCommand(cmdLabel);
			if (command.completer == null) {
				command.completer = new BukkitCompleter();
			}
			command.completer.addCompleter(label, m, obj);
		} else if (map.getCommand(cmdLabel) instanceof PluginCommand) {
			try {
				Object command = map.getCommand(cmdLabel);
				Field field = command.getClass().getDeclaredField("completer");
				field.setAccessible(true);
				if (field.get(command) == null) {
					BukkitCompleter completer = new BukkitCompleter();
					completer.addCompleter(label, m, obj);
					field.set(command, completer);
				} else if (field.get(command) instanceof BukkitCompleter) {
					BukkitCompleter completer = (BukkitCompleter) field.get(command);
					completer.addCompleter(label, m, obj);
				} else {
					Lang.console(LangKey.Command.COMPLETER_ALREADY_REGISTERED, m.getName());
				}
			} catch (Exception ex) {
				Lang.error(ex);
			}
		}
	}

	private void defaultCommand(CommandArgs args) {
		Lang.msg(args.getSender(), LangKey.Command.COMMAND_NOT_HANDLED, args.getLabel());
	}

	public void unregister(final String command) {
		if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
			final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
			try {
				final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				map = (CommandMap) field.get(manager);
				final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field2.setAccessible(true);
				@SuppressWarnings("unchecked")
				final Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2
						.get(map);
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
}

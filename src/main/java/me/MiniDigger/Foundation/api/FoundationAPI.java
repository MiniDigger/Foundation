package me.MiniDigger.Foundation.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import me.MiniDigger.Foundation.FoundationMain;
import me.MiniDigger.Foundation.handler.command.CommandHandler;
import me.MiniDigger.Foundation.handler.command.CommandWrongParamsException;
import me.MiniDigger.Foundation.handler.lang.LangHandler;
import me.MiniDigger.Foundation.handler.lang.LangKeyProvider;
import me.MiniDigger.Foundation.handler.module.Module;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * The class is the main gateways for modules to access stuff from foundation
 * <br>
 * Modules should use this class since it unregisters stuff if a module gets
 * unregistered
 */
public class FoundationAPI {

	private static final Map<String, LangKeyProvider> langKeys = new HashMap<>();

	public static void registerLangKeys(final LangKeyProvider key, final Module m) {
		Validate.notNull(key);
		Validate.notNull(m);

		LangHandler.getInstance().addAdditionalLangKeys(key.values());
		langKeys.put(m.getDescription().name(), key);
	}

	public static void unregisterLangKeys(final LangKeyProvider key, final Module m) {
		Validate.notNull(key);
		Validate.notNull(m);

		LangHandler.getInstance().removeAdditionalLangKeys(key.values());
		langKeys.remove(m.getDescription().name());
	}

	private static final Map<String, Listener> listener = new HashMap<>();

	public static void registerEvents(final Listener listener, final Module m) {
		Validate.notNull(listener);
		Validate.notNull(m);

		Bukkit.getServer().getPluginManager().registerEvents(listener, FoundationMain.getInstance());
		FoundationAPI.listener.put(m.getDescription().name(), listener);
	}

	public static void unregisterEvents(final Listener listener, final Module m) {
		Validate.notNull(listener);
		Validate.notNull(m);

		HandlerList.unregisterAll(listener);
		FoundationAPI.listener.remove(m.getDescription().name());
	}

	private static final Map<String, Object> commandExecutors = new HashMap<>();

	public static void registerCommands(final Object obj, final Module m) {
		Validate.notNull(obj);
		Validate.notNull(m);

		try {
			CommandHandler.getInstance().register(obj);
			commandExecutors.put(m.getDescription().name(), obj);
		} catch (final CommandWrongParamsException e) {
			System.err.println("Go tell " + m.getDescription().author() + ", the author of the foundation module " + m.getDescription().name()
					+ " that he registered the commands in " + obj.getClass().getName() + " wrong!");
			e.printStackTrace();
		}
	}

	public static void unregisterCommands(final Object obj, final Module m) {
		Validate.notNull(obj);
		Validate.notNull(m);

		CommandHandler.getInstance().unregister(obj);
		commandExecutors.remove(m.getDescription().name());
	}

	public static void onDisable(final Module m) {
		Validate.notNull(m);

		unregisterLangKeys(langKeys.get(m.getDescription().name()), m);
		unregisterEvents(listener.get(m.getDescription().name()), m);
		unregisterCommands(commandExecutors.get(m.getDescription().name()), m);

	}
}

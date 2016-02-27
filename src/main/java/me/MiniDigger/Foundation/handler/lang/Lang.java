package me.MiniDigger.Foundation.handler.lang;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import me.MiniDigger.Foundation.FoundationMain;

public class Lang {
	public static String translate(LangKey key, LangType lang, String... args) {
		String raw = LangHandler.getInstance().getLangStorage(lang).get(key);

		for (int i = 0; i < args.length; i++) {
			raw = raw.replace("%" + i + "%", args[i]);
		}

		return raw;
	}

	public static String translate(LangKey key, String... args) {
		return translate(key, LangHandler.getInstance().getDefaultLang(), args);
	}

	public static void msg(CommandSender sender, LangKey key, LangType lang, String... args) {
		sender.sendMessage(translate(key, lang, args));
	}

	public static void msg(CommandSender sender, LangKey key, String... args) {
		sender.sendMessage(translate(key, args));
	}

	public static void console(LangKey key, String... args) {
		if (FoundationMain.isInTestMode()) {
			msg(FoundationMain.getTestCommandSender(), key, args);
		} else {
			msg(Bukkit.getConsoleSender(), key, args);
		}
	}

	public static void console(LangKey key, LangType lang, String... args) {
		if (FoundationMain.isInTestMode()) {
			msg(FoundationMain.getTestCommandSender(), key, lang, args);
		} else {
			msg(Bukkit.getConsoleSender(), key, lang, args);
		}
	}

	public static void error(Exception e) {
		e.printStackTrace();
		// TODO better error handeling
	}
}

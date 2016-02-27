package me.MiniDigger.Foundation.handler.lang;

import java.util.ArrayList;
import java.util.List;

public class LangKey {
	private String parentName;
	private String name;
	private String defaultValue;

	public LangKey(String parentName, String name, String defaultValue) {
		this.parentName = parentName;
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public String getFullName() {
		return getParentName() + "." + getName();
	}

	public String getParentName() {
		return parentName;
	}

	public String getName() {
		return name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public static class Foundation {
		public static final String NAME = "foundation";
		public static final LangKey ENABLE = new LangKey(NAME, "enable", "Enableing %0% by MiniDigger");

		public static List<LangKey> values() {
			List<LangKey> result = new ArrayList<LangKey>();

			result.add(ENABLE);

			return result;
		}
	}

	public static class Lang {
		public static final String NAME = "lang";
		public static final LangKey COULD_NOT_LOAD_FILE = new LangKey(NAME, "could_not_load_file",
				"Could not load langfile %0%!");
		public static final LangKey LOAD = new LangKey(NAME, "load", "Loaded %0% langs");
		public static final LangKey LOAD_STORAGE = new LangKey(NAME, "load_storage", "Loaded %0% keys in lang %1%");

		public static List<LangKey> values() {
			List<LangKey> result = new ArrayList<LangKey>();

			result.add(LOAD);

			return result;
		}
	}

	public static class Command {
		public static final String NAME = "command";

		public static final LangKey COMPLETER_ALREADY_REGISTERED = new LangKey(NAME, "completer_already_registered",
				"Unable to register tab completer %0%. A tab completer is already registered for that command!");
		public static final LangKey HELP_TITLE = new LangKey(NAME, "help_title", "All commands for %0%");
		public static final LangKey HELP_BODY = new LangKey(NAME, "help_body", "Below is a list of all %0% commands:");
		public static final LangKey COMPLETER_UNEXPECTED_RETURN_TYPE = new LangKey(NAME,
				"completer_unexpected_return_type", "Unable to register tab completer %0%. Unexpected return type");
		public static final LangKey COMPLETER_UNEXPECTED_METHOD_ARGS = new LangKey(NAME,
				"completer_unexpected_method_args",
				"Unable to register tab completer %0%. Unexpected method arguments");
		public static final LangKey COMMAND_UNEXPECTED_METHOD_ARGS = new LangKey(NAME,
				"completer_unexpected_method_args", "Unable to register command %0%. Unexpected method arguments");
		public static final LangKey COMMAND_NOT_HANDLED = new LangKey(NAME, "command_not_handled",
				"%0% is not handled! Oh noes!");
		public static final LangKey ONLY_INGAME = new LangKey(NAME, "only_ingame",
				"This command is only performable in game");

		public static List<LangKey> values() {
			List<LangKey> result = new ArrayList<>();

			result.add(COMPLETER_ALREADY_REGISTERED);
			result.add(HELP_TITLE);
			result.add(HELP_BODY);
			result.add(COMPLETER_UNEXPECTED_RETURN_TYPE);
			result.add(COMPLETER_UNEXPECTED_METHOD_ARGS);
			result.add(COMMAND_UNEXPECTED_METHOD_ARGS);
			result.add(COMMAND_NOT_HANDLED);
			result.add(ONLY_INGAME);

			return result;
		}
	}

	public static class TEST {
		public static final String NAME = "test";

		public static final LangKey TEST = new LangKey(NAME, "test", "Just a jUnit test %0%");
		public static final LangKey TEST_VARS = new LangKey(NAME, "test_vars",
				"Just %0% a %1% bunch %2% of %3% vars %4%");

		public static List<LangKey> values() {
			List<LangKey> result = new ArrayList<>();

			result.add(TEST);
			result.add(TEST_VARS);

			return result;
		}
	}

	public static List<LangKey> values() {
		List<LangKey> result = new ArrayList<LangKey>();

		result.addAll(Foundation.values());
		result.addAll(Lang.values());
		result.addAll(Command.values());
		result.addAll(TEST.values());

		return result;
	}

	public static LangKey valueOf(String key) {
		for (LangKey k : values()) {
			if (k.getFullName().equals(key)) {
				return k;
			}
		}
		return null;
	}
}

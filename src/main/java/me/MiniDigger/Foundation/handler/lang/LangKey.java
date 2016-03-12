package me.MiniDigger.Foundation.handler.lang;

import java.util.ArrayList;
import java.util.List;

public class LangKey {
	private final String parentName;
	private final String name;
	private final String defaultValue;
	
	public LangKey(final String parentName, final String name, final String defaultValue) {
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
	
	public static class Foundation extends LangKeyProvider {
		public static final String NAME = "foundation";
		public static final LangKey ENABLE = new LangKey(NAME, "enable", "Enableing %0% by MiniDigger");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<LangKey>();
			
			result.add(ENABLE);
			
			return result;
		}
	}
	
	public static class Lang extends LangKeyProvider {
		public static final String NAME = "lang";
		public static final LangKey COULD_NOT_LOAD_FILE = new LangKey(NAME, "could_not_load_file", "Could not load langfile %0%!");
		public static final LangKey LOAD = new LangKey(NAME, "load", "Loaded %0% langs");
		public static final LangKey LOAD_STORAGE = new LangKey(NAME, "load_storage", "Loaded %0% keys in lang %1%");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<LangKey>();
			
			result.add(LOAD);
			result.add(COULD_NOT_LOAD_FILE);
			result.add(LOAD_STORAGE);
			
			return result;
		}
	}
	
	public static class Config extends LangKeyProvider {
		
		public static final String NAME = "config";
		public static final LangKey LOAD = new LangKey(NAME, "load", "Loaded %0% langs");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<LangKey>();
			
			result.add(LOAD);
			
			return result;
		}
	}
	
	public static class Command extends LangKeyProvider {
		public static final String NAME = "command";
		
		public static final LangKey COMPLETER_ALREADY_REGISTERED = new LangKey(NAME, "completer_already_registered",
				"Unable to register tab completer %0%. A tab completer is already registered for that command!");
		public static final LangKey HELP_TITLE = new LangKey(NAME, "help_title", "All commands for %0%");
		public static final LangKey HELP_BODY = new LangKey(NAME, "help_body", "Below is a list of all %0% commands:");
		public static final LangKey COMPLETER_UNEXPECTED_RETURN_TYPE = new LangKey(NAME, "completer_unexpected_return_type",
				"Unable to register tab completer %0%. Unexpected return type");
		public static final LangKey COMPLETER_UNEXPECTED_METHOD_ARGS = new LangKey(NAME, "completer_unexpected_method_args",
				"Unable to register tab completer %0%. Unexpected method arguments");
		public static final LangKey COMMAND_UNEXPECTED_METHOD_ARGS = new LangKey(NAME, "command_unexpected_method_args",
				"Unable to register command %0%. Unexpected method arguments");
		public static final LangKey COMMAND_NOT_HANDLED = new LangKey(NAME, "command_not_handled", "%0% is not handled! Oh noes!");
		public static final LangKey ONLY_INGAME = new LangKey(NAME, "only_ingame", "This command is only performable in game");
		public static final LangKey COMMAND_UNREGISTER = new LangKey(NAME, "completer_unregister",
				"Unable to unregister command %0%. Unexpected method arguments");
		public static final LangKey RELOCATION = new LangKey(NAME, "relocation", "Relocation: Moving %0% to %1%");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<>();
			
			result.add(COMPLETER_ALREADY_REGISTERED);
			result.add(HELP_TITLE);
			result.add(HELP_BODY);
			result.add(COMPLETER_UNEXPECTED_RETURN_TYPE);
			result.add(COMPLETER_UNEXPECTED_METHOD_ARGS);
			result.add(COMMAND_UNEXPECTED_METHOD_ARGS);
			result.add(COMMAND_NOT_HANDLED);
			result.add(ONLY_INGAME);
			result.add(COMMAND_UNREGISTER);
			result.add(RELOCATION);
			
			return result;
		}
	}
	
	public static class Module extends LangKeyProvider {
		public static final String NAME = "module";
		
		public static final LangKey ERROR_NO_MAIN = new LangKey(NAME, "error_no_main", "Cannot find main class %0%");
		public static final LangKey ERROR_NO_EXTEND = new LangKey(NAME, "error_no_extend", "Main class %0% does not extend Module");
		public static final LangKey ERROR_NO_CONSTRUCTOR = new LangKey(NAME, "error_no_constructor", "No public constructor in main class %0%");
		public static final LangKey ERROR_ABNORMAL_TYPE = new LangKey(NAME, "error_abnormal_type", "Abnormal plugin type for main class %0%");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<>();
			
			result.add(ERROR_NO_MAIN);
			result.add(ERROR_NO_EXTEND);
			result.add(ERROR_NO_CONSTRUCTOR);
			result.add(ERROR_ABNORMAL_TYPE);
			
			return result;
		}
	}
	
	public static class TEST extends LangKeyProvider {
		public static final String NAME = "test";
		
		public static final LangKey TEST = new LangKey(NAME, "test", "Just a jUnit test %0%");
		public static final LangKey TEST_VARS = new LangKey(NAME, "test_vars", "Just %0% a %1% bunch %2% of %3% vars %4%");
		
		@Override
		public List<LangKey> values() {
			final List<LangKey> result = new ArrayList<>();
			
			result.add(TEST);
			result.add(TEST_VARS);
			
			return result;
		}
	}
	
	public static List<LangKey> values() {
		final List<LangKey> result = new ArrayList<LangKey>();
		
		result.addAll(new Foundation().values());
		result.addAll(new Lang().values());
		result.addAll(new Command().values());
		result.addAll(new Module().values());
		result.addAll(new TEST().values());
		result.addAll(new Config().values());
		result.addAll(LangHandler.getInstance().getAdditionalLangKeys());
		
		return result;
	}
	
	public static LangKey valueOf(final String key) {
		for (final LangKey k : values()) {
			if (k.getFullName().equals(key)) {
				return k;
			}
		}
		return null;
	}
}

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

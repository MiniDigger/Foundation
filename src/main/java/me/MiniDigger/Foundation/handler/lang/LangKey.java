package me.MiniDigger.Foundation.handler.lang;

public class LangKey {
	private String name;
	private String defaultValue;

	public LangKey(String name, String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	class Foundation{
		public LangKey ENABLE = new LangKey("enable","Enableing %0% by MiniDigger");
	}
}

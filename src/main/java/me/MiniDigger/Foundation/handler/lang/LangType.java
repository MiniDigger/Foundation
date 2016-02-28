package me.MiniDigger.Foundation.handler.lang;

public class LangType {

	public static final LangType en_US = new LangType("en_US");

	public String key;

	public LangType(final String key) {
		this.key = key;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LangType) {
			return ((LangType) obj).key.equals(key);
		} else {
			return false;
		}
	}

}

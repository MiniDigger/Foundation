package me.MiniDigger.Foundation.handler.lang;

import java.util.Map;

public class LangStorage {
	private Map<LangKey, String> data;

	public String get(LangKey key) {
		if (data.containsKey(key)) {
			return key.getDefaultValue();
		} else {
			return data.get(key);
		}
	}
}

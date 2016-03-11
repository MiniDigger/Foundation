package me.MiniDigger.Foundation.handler.lang;

import java.util.ArrayList;
import java.util.List;

public class TestLangKeyProvider extends LangKeyProvider {

	public static final LangKey TEST = new LangKey("testlangkeyprovider", "test", "Testing");

	@Override
	public List<LangKey> values() {
		List<LangKey> result = new ArrayList<>();
		result.add(TEST);
		return result;
	}

}

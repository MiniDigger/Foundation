package me.MiniDigger.Foundation.api;

import me.MiniDigger.Foundation.handler.lang.LangHandler;
import me.MiniDigger.Foundation.handler.lang.LangKeyProvider;

/**
 * The class is the main gateways for modules to access stuff from foundation
 */
public class FoundationAPI {
	public static void registerLangKeys(LangKeyProvider key) {
		LangHandler.getInstance().addAdditionalLangKeys(key.values());
	}
}

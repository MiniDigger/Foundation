package me.MiniDigger.Foundation.handler.module;

import org.apache.commons.lang3.ArrayUtils;

import me.MiniDigger.Foundation.handler.lang.Lang;
import me.MiniDigger.Foundation.handler.lang.LangKey;

public class InvalidModuleException extends Exception {

	private static final long serialVersionUID = -5545845044454915809L;

	public InvalidModuleException(LangKey key, Exception ex, String... args) {
		super(Lang.translate(key,
				ArrayUtils.add(args, ": " + (ex != null ? (ex.getClass().getName() + ": " + ex.getMessage()) : ""))));
	}
}

package me.MiniDigger.Foundation.handler;

public class FoundationHandler {

	/**
	 * A unique name of the handler, default to getClass#getSimpleName
	 *
	 * @return
	 */
	public String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * Called when the plugin gets enabled
	 *
	 * @return success
	 */
	public boolean onLoad() {
		return true;
	}

	/**
	 * Called when all modules and handler got loaded
	 *
	 * @return success
	 */
	public boolean onEnable() {
		return true;
	}

	/**
	 * Called when the plugin gets disabled
	 *
	 * @return success
	 */
	public boolean onDisable() {
		return true;
	}
}

package me.MiniDigger.Foundation.handler.command;

public class CommandArgsNotMatchedException extends Exception {

	private static final long serialVersionUID = 9068136215600086292L;

	public CommandArgsNotMatchedException(final int index, final String expected, final String input) {
		super("ArgIndex: " + index + ", expected:" + expected + ", got: " + input);
	}
}

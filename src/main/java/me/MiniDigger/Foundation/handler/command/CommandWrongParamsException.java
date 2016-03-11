package me.MiniDigger.Foundation.handler.command;

public class CommandWrongParamsException extends Exception {

	private static final long serialVersionUID = 7191773626619205339L;

	public CommandWrongParamsException(final String string) {
		super(string);
	}
}

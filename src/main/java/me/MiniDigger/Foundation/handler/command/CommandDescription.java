package me.MiniDigger.Foundation.handler.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDescription {
	public String name();

	public String usage() default "";

	public String permission();

	public boolean console() default true;
}

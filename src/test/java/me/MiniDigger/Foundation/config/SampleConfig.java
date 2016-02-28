package me.MiniDigger.Foundation.config;

import me.MiniDigger.Foundation.handler.config.Config;
import me.MiniDigger.Foundation.handler.config.Storeable;

public class SampleConfig extends Config{

	@Storeable
	public String string;
	@Storeable
	public Boolean bool;
	@Storeable
	public Integer integer;

}

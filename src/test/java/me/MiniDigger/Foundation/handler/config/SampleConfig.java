package me.MiniDigger.Foundation.handler.config;

import java.util.List;

import org.bukkit.Location;

public class SampleConfig extends Config {

	@Storeable
	public String string;
	@Storeable
	public Boolean bool;
	@Storeable
	public Integer integer;
	@Storeable
	public int test;
	@Storeable
	public boolean test2;
	@Storeable
	public Location loc;
	@Storeable
	@ConfigList(clazz = String.class)
	public List<String> stringList;
	@Storeable
	@ConfigList(clazz = Boolean.class)
	public List<Boolean> boolList;
	@Storeable
	@ConfigList(clazz = Integer.class)
	public List<Integer> intList;

}

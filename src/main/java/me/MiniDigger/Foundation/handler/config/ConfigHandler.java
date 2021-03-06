package me.MiniDigger.Foundation.handler.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.config.adapters.BoolConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.BooleanConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.IntConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.IntegerConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.ListConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.LocationConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.StringConfigAdapter;
import me.MiniDigger.Foundation.handler.lang.Lang;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler extends FoundationHandler {
	
	private static ConfigHandler INSTANCE;
	private final List<ConfigAdapter> adapters = new ArrayList<>();
	private ConfigAdapter defaultAdapter;
	
	@Override
	public boolean onLoad() {
		initAdapter();
		return true;
	}
	
	@Override
	public boolean onEnable() {
		return true;
	}
	
	private void initAdapter() {
		registerAdapter(new StringConfigAdapter());
		registerAdapter(new BooleanConfigAdapter());
		registerAdapter(new BoolConfigAdapter());
		registerAdapter(new IntegerConfigAdapter());
		registerAdapter(new IntConfigAdapter());
		registerAdapter(new LocationConfigAdapter());
		registerAdapter(new ListConfigAdapter());
		
		defaultAdapter = new StringConfigAdapter();
	}
	
	public void registerAdapter(final ConfigAdapter adapter) {
		adapters.add(adapter);
	}
	
	public Config loadConfig(final Class<? extends Config> config, final File file) {
		if (adapters.size() == 0) {
			initAdapter();
		}
		
		Config c;
		try {
			c = config.newInstance();
		} catch (InstantiationException | IllegalAccessException e2) {
			Lang.error(e2);
			return null;
		}
		c.setFileName(file.getName());
		
		final FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		try {
			fc.load(file);
		} catch (IOException | InvalidConfigurationException e1) {
			Lang.error(e1);
		}
		
		for (final Field f : config.getFields()) {
			if (f.isAnnotationPresent(Storeable.class)) {
				final ConfigAdapter a = getAdapter(f.getType());
				if (a == null) {
					System.out.println("no adapter for field " + f.getName());
					continue;
				}
				try {
					if (f.getType().equals(List.class)) {
						if (f.isAnnotationPresent(ConfigList.class)) {
							final ConfigList cl = f.getAnnotation(ConfigList.class);
							final List<String> l = fc.getStringList(f.getName());
							final StringBuilder s = new StringBuilder();
							s.append(cl.clazz().getName() + "&�&");
							for (final String ss : l) {
								s.append(ss + "&�&");
							}
							f.set(c, a.fromString(s.toString()));
						} else {
							Lang.error(new IllegalArgumentException("@Storable list " + f.getName() + " is not a @ConfigList?!"));
						}
					} else {
						f.set(c, a.fromString(fc.getString(f.getName())));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					Lang.error(e);
				}
			}
		}
		return c;
	}
	
	public void saveConfig(final Config config, final File file) {
		if (adapters.size() == 0) {
			initAdapter();
		}
		
		final FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		
		for (final Field f : config.getClass().getFields()) {
			if (f.isAnnotationPresent(Storeable.class)) {
				final ConfigAdapter a = getAdapter(f.getType());
				if (a == null) {
					System.out.println("no adapter for field " + f.getName());
					continue;
				}
				try {
					if (f.getType().equals(List.class)) {
						if (f.isAnnotationPresent(ConfigList.class)) {
							final ConfigList cl = f.getAnnotation(ConfigList.class);
							@SuppressWarnings("unchecked")
							List<Object> list = (List<Object>) f.get(config);
							list = new ArrayList<>(list);
							list.add(0, cl.clazz().getName());
							final String s = a.toString(list);
							final String[] ss = s.split(Pattern.quote("&�&"));
							final List<String> l = Arrays.asList(ss);
							fc.set(f.getName(), l);
						} else {
							Lang.error(new IllegalArgumentException("@Storable list " + f.getName() + " is not a @ConfigList?!"));
						}
					} else {
						fc.set(f.getName(), a.toString(f.get(config)));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					Lang.error(e);
				}
			}
		}
		
		try {
			fc.save(file);
		} catch (final IOException e) {
			Lang.error(e);
		}
	}
	
	public ConfigAdapter getAdapter(final Class<?> clazz) {
		for (final ConfigAdapter a : adapters) {
			if (a.getClazz().equals(clazz)) {
				return a;
			}
		}
		return defaultAdapter;
	}
	
	public static ConfigHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConfigHandler();
		}
		return INSTANCE;
	}
}

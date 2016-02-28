package me.MiniDigger.Foundation.handler.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.MiniDigger.Foundation.config.SampleConfig;
import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.config.adapters.BooleanConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.IntegerConfigAdapter;
import me.MiniDigger.Foundation.handler.config.adapters.StringConfigAdapter;
import me.MiniDigger.Foundation.handler.lang.Lang;

public class ConfigHandler extends FoundationHandler {

	private static ConfigHandler INSTANCE;
	private List<ConfigAdapter> adapters = new ArrayList<>();
	private ConfigAdapter defaultAdapter;

	@Override
	public boolean onLoad() {
		initAdapter();
		return super.onLoad();
	}

	private void initAdapter() {
		registerAdapter(new StringConfigAdapter());
		registerAdapter(new BooleanConfigAdapter());
		registerAdapter(new IntegerConfigAdapter());
	}

	public void registerAdapter(ConfigAdapter adapter) {
		adapters.add(adapter);
	}

	public Config loadConfig(Class<? extends Config> config, File file) {
		Config c;
		try {
			c = config.newInstance();
		} catch (InstantiationException | IllegalAccessException e2) {
			Lang.error(e2);
			return null;
		}
		c.setFileName(file.getName());

		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		try {
			fc.load(file);
		} catch (IOException | InvalidConfigurationException e1) {
			Lang.error(e1);
		}

		for (Field f : config.getFields()) {
			if (f.isAnnotationPresent(Storeable.class)) {
				ConfigAdapter a = getAdapter(f.getType());
				try {
					f.set(c, a.fromString(fc.getString(f.getName())));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					Lang.error(e);
				}
			}
		}
		return c;
	}

	public void saveConfig(SampleConfig config, File file) {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

		for (Field f : config.getClass().getFields()) {
			if (f.isAnnotationPresent(Storeable.class)) {
				ConfigAdapter a = getAdapter(f.getType());
				try {
					fc.set(f.getName(), a.toString(f.get(config)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					Lang.error(e);
				}
			}
		}

		try {
			fc.save(file);
		} catch (IOException e) {
			Lang.error(e);
		}
	}

	public ConfigAdapter getAdapter(Class<?> clazz) {
		for (ConfigAdapter a : adapters) {
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

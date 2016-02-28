package me.MiniDigger.Foundation.handler.module;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.lang.Lang;
import me.MiniDigger.Foundation.handler.lang.module.InvalidModuleException;
import me.MiniDigger.Foundation.handler.lang.module.ModuleLoader;

public class ModuleHandler extends FoundationHandler {

	private static ModuleHandler INSTANCE;

	private List<Module> modules = new ArrayList<>();

	private File moduleFolder = new File("src\\test\\resources\\testModuleFolder");

	private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
	private final Map<String, ModuleLoader> loaders = new LinkedHashMap<String, ModuleLoader>();

	@Override
	public boolean onLoad() {
		loadModules();
		return super.onLoad();
	}

	@Override
	public boolean onEnable() {
		for (Module m : modules) {
			m.onEnable();
		}
		return super.onEnable();
	}

	@Override
	public boolean onDisable() {
		for (Module m : modules) {
			m.onDisable();
		}
		return super.onDisable();
	}

	public void loadModules() {
		for (File f : moduleFolder.listFiles((dir, name) -> {
			return name.endsWith(".jar");
		})) {
			try {
				ModuleLoader loader = new ModuleLoader(getClass().getClassLoader(), f.toURI().toURL());
				loaders.put(loader.getDescription().name(), loader);
				modules.add(loader.getModule());
			} catch (MalformedURLException | InvalidModuleException | NoSuchFieldException | SecurityException
					| IllegalArgumentException | IllegalAccessException e) {
				Lang.error(e);
			}
		}
	}

	public Class<?> getClassByName(final String name) {
		Class<?> cachedClass = classes.get(name);

		if (cachedClass != null) {
			return cachedClass;
		} else {
			for (final String current : loaders.keySet()) {
				final ModuleLoader loader = loaders.get(current);

				try {
					cachedClass = loader.findClass(name, false);
				} catch (final ClassNotFoundException e) {
				}
				if (cachedClass != null) {
					return cachedClass;
				}
			}
		}
		return null;
	}

	public void setClass(final String name, final Class<?> clazz) {
		if (!classes.containsKey(name)) {
			classes.put(name, clazz);
		}
	}

	public void removeClass(final String name) {
		@SuppressWarnings("unused")
		Class<?> clazz = classes.remove(name);
		clazz = null; // Bye!
	}

	public static ModuleHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ModuleHandler();
		}
		return INSTANCE;
	}
}
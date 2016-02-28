package me.MiniDigger.Foundation.handler.module;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import me.MiniDigger.Foundation.handler.lang.LangKey;

/**
 * A ClassLoader for modules, to allow shared classes across multiple modules
 * <br>
 * Idea copied from the bukkit project at bukkit.org
 */
public class ModuleLoader extends URLClassLoader {

	private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
	private Module module;
	private ModuleDescription desc;

	public ModuleLoader(final ClassLoader parent, final URL url) throws InvalidModuleException, MalformedURLException,
			NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		super(new URL[] { url }, parent);

		String main = null;
		Field f = ClassLoader.class.getDeclaredField("classes");
		f.setAccessible(true);

		Reflections reflections = new Reflections("");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ModuleDescription.class);
		for (Class<?> clazz : annotated) {
			if (clazz.isAnnotationPresent(ModuleDescription.class)) {
				main = clazz.getName();
				desc = clazz.getAnnotation(ModuleDescription.class);
				break;
			}
		}

		if (main == null) {
			throw new InvalidModuleException(LangKey.Module.ERROR_NO_MAIN, null, "");
		}

		try {
			Class<?> jarClass;
			try {
				jarClass = Class.forName(main, true, this);
			} catch (final ClassNotFoundException ex) {
				throw new InvalidModuleException(LangKey.Module.ERROR_NO_MAIN, ex, main);
			}

			Class<? extends Module> pluginClass;
			try {
				pluginClass = jarClass.asSubclass(Module.class);
			} catch (final ClassCastException ex) {
				throw new InvalidModuleException(LangKey.Module.ERROR_NO_EXTEND, ex, main);
			}

			module = pluginClass.newInstance();
		} catch (final IllegalAccessException ex) {
			throw new InvalidModuleException(LangKey.Module.ERROR_NO_CONSTRUCTOR, ex, main);
		} catch (final InstantiationException ex) {
			throw new InvalidModuleException(LangKey.Module.ERROR_ABNORMAL_TYPE, ex, main);
		}
	}

	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		return findClass(name, true);
	}

	public Class<?> findClass(final String name, final boolean checkGlobal) throws ClassNotFoundException {
		if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
			throw new ClassNotFoundException(name);
		}
		Class<?> result = classes.get(name);

		if (result == null) {
			if (checkGlobal) {
				result = ModuleHandler.getInstance().getClassByName(name);
			}

			if (result == null) {
				result = super.findClass(name);

				if (result != null) {
					ModuleHandler.getInstance().setClass(name, result);
				}
			}

			classes.put(name, result);
		}

		return result;
	}

	public Set<String> getClasses() {
		return classes.keySet();
	}

	public Module getModule() {
		return module;
	}

	public ModuleDescription getDescription() {
		return desc;
	}
}
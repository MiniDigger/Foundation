package me.MiniDigger.Foundation.handler.config.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.MiniDigger.Foundation.handler.config.ConfigAdapter;
import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;
import me.MiniDigger.Foundation.handler.lang.Lang;

public class ListConfigAdapter extends ConfigAdapter {

	@Override
	public Class<?> getClazz() {
		return List.class;
	}

	@Override
	public Object fromString(final String source) {
		final String[] s = source.split(Pattern.quote("&§&"));
		try {
			final Class<?> c = Class.forName(s[0]);
			final ConfigAdapter a = ConfigHandler.getInstance().getAdapter(c);
			if (a == null) {
				throw new ConfigAdapterNotApplicable(this, source);
			}
			final List<Object> list = new ArrayList<>();
			for (int i = 1; i < s.length; i++) {
				list.add(a.fromString(s[i]));
			}
			return list;
		} catch (final ClassNotFoundException e) {
			Lang.error(e);
			throw new ConfigAdapterNotApplicable(this, source);
		}
	}

	@Override
	public String toString(final Object source) {
		if (source instanceof List) {
			@SuppressWarnings("unchecked")
			final List<Object> list = (List<Object>) source;
			final Object obj = list.get(0);
			if (!(obj instanceof String)) {
				throw new ConfigAdapterNotApplicable(this, source);
			}

			try {
				final Class<?> c = Class.forName((String) obj);
				final ConfigAdapter a = ConfigHandler.getInstance().getAdapter(c);
				if (a == null) {
					throw new ConfigAdapterNotApplicable(this, source);
				}
				final StringBuilder result = new StringBuilder();
				for (int i = 1; i < list.size(); i++) {
					final Object o = list.get(i);
					result.append(a.toString(o) + "&§&");
				}
				return result.toString();
			} catch (final ClassNotFoundException e) {
				Lang.error(e);
				throw new ConfigAdapterNotApplicable(this, source);
			}
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

}

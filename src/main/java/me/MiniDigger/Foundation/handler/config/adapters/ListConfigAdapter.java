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
	public Object fromString(String source) {
		String[] s = source.split(Pattern.quote("&§&"));
		try {
			Class<?> c = Class.forName(s[0]);
			ConfigAdapter a = ConfigHandler.getInstance().getAdapter(c);
			if (a == null) {
				throw new ConfigAdapterNotApplicable(this, source);
			}
			List<Object> list = new ArrayList<>();
			for (int i = 1; i < s.length; i++) {
				list.add(a.fromString(s[i]));
			}
			return list;
		} catch (ClassNotFoundException e) {
			Lang.error(e);
			throw new ConfigAdapterNotApplicable(this, source);
		}
	}

	@Override
	public String toString(Object source) {
		if (source instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) source;
			Object obj = list.get(0);
			if (!(obj instanceof String)) {
				throw new ConfigAdapterNotApplicable(this, source);
			}

			try {
				Class<?> c = Class.forName((String) obj);
				ConfigAdapter a = ConfigHandler.getInstance().getAdapter(c);
				if (a == null) {
					throw new ConfigAdapterNotApplicable(this, source);
				}
				StringBuilder result = new StringBuilder();
				for (int i = 1; i < list.size(); i++) {
					Object o = list.get(i);
					result.append(a.toString(o) + "&§&");
				}
				return result.toString();
			} catch (ClassNotFoundException e) {
				Lang.error(e);
				throw new ConfigAdapterNotApplicable(this, source);
			}
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

}

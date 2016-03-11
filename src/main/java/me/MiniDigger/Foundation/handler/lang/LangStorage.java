package me.MiniDigger.Foundation.handler.lang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LangStorage {
	private final Map<LangKey, String> data = new HashMap<>();
	private LangType lang;

	public LangStorage(final LangType defaultLang) {
		lang = defaultLang;
	}

	public String get(final LangKey key) {
		if (!data.containsKey(key)) {
			return key.getDefaultValue();
		} else {
			return data.get(key);
		}
	}

	public boolean load(final File f) {
		try (Scanner sc = new Scanner(f);) {
			lang = new LangType(f.getName().replace(".flang", ""));

			while (sc.hasNextLine()) {
				final String l = sc.nextLine();
				if (!l.contains("=") || l.startsWith("#")) {
					continue;
				}

				final String[] line = l.split("=");
				final String key = line[0];
				String value = "";
				if (line.length > 2) {
					value = l.replace(key + "=", "");
				} else {
					value = line[1];
				}

				LangKey langKey = LangKey.valueOf(key);
				if (langKey == null) {
					if (key.contains(".")) {
						final String[] k = key.split(Pattern.quote("."));
						langKey = new LangKey(k[0], k[1], value);
					} else {
						continue;
					}
				}
				data.put(langKey, value);
			}

			Lang.console(LangKey.Lang.LOAD_STORAGE, data.size() + "", lang.key);
			return true;
		} catch (final IOException ex) {
			Lang.error(ex);
			return false;
		}
	}

	public boolean save(final File f) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(f));) {
			pw.println("########################");
			pw.println("# Foundation Lang File #");
			pw.println("# Lang: " + getLang().key);
			pw.println("# Author: MiniDigger   #");
			pw.println("########################");

			String oldParentName = "";
			for (final LangKey key : LangKey.values()) {
				// comment before new section
				if (!key.getParentName().equals(oldParentName)) {
					oldParentName = key.getParentName();
					String ph = "";
					for (int i = 0; i <= oldParentName.length(); i++) {
						ph += "#";
					}
					pw.println("##" + ph + "##");
					pw.println("#  " + oldParentName + " #");
					pw.println("##" + ph + "##");
				}

				// write
				final String k = key.getFullName();
				final String v = get(key);
				pw.println(k + "=" + v);
			}

			pw.flush();
			pw.close();
			return true;
		} catch (final IOException ex) {
			Lang.error(ex);
			return false;
		}
	}

	public LangType getLang() {
		return lang;
	}

	public void setLang(final LangType lang) {
		this.lang = lang;
	}
}

package me.MiniDigger.Foundation.handler.lang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LangStorage {
	private Map<LangKey, String> data = new HashMap<>();
	private LangType lang;

	public LangStorage(LangType defaultLang) {
		this.lang = defaultLang;
	}

	public String get(LangKey key) {
		if (!data.containsKey(key)) {
			return key.getDefaultValue();
		} else {
			return data.get(key);
		}
	}

	public boolean load(File f) {
		try (Scanner sc = new Scanner(f);) {
			lang = new LangType(f.getName().replace(".flang", ""));

			while (sc.hasNextLine()) {
				String l = sc.nextLine();
				if (!l.contains("=") || l.startsWith("#")) {
					continue;
				}

				String[] line = l.split("=");
				String key = line[0];
				String value = "";
				if (line.length > 2) {
					value = l.replace(key + "=", "");
				} else {
					value = line[1];
				}

				LangKey langKey = LangKey.valueOf(key);
				data.put(langKey, value);
			}

			Lang.console(LangKey.Lang.LOAD_STORAGE, data.size() + "", lang.key);
			return true;
		} catch (IOException ex) {
			Lang.error(ex);
			return false;
		}
	}

	public boolean save(File f) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(f));) {
			pw.println("########################");
			pw.println("# Foundation Lang File #");
			pw.println("# Lang: " + getLang().key);
			pw.println("# Author: MiniDigger   #");
			pw.println("########################");

			String oldParentName = "";
			for (LangKey key : LangKey.values()) {
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
				String k = key.getFullName();
				String v = get(key);
				pw.println(k + "=" + v);
			}

			pw.flush();
			pw.close();
			return true;
		} catch (IOException ex) {
			Lang.error(ex);
			return false;
		}
	}

	public LangType getLang() {
		return lang;
	}

	public void setLang(LangType lang) {
		this.lang = lang;
	}
}

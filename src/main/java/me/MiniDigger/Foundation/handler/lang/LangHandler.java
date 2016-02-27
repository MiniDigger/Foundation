package me.MiniDigger.Foundation.handler.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.MiniDigger.Foundation.handler.FoundationHandler;

public class LangHandler extends FoundationHandler {

	private static LangHandler INSTANCE;

	private LangType defaultLang = new LangType("en_US");

	private LangStorage defaultLangStorage = new LangStorage(defaultLang);
	private List<LangStorage> langStorages = new ArrayList<>();
	private List<String> langs = new ArrayList<>();

	private File langFolder;

	@Override
	public boolean onLoad() {
		loadLangs();
		return super.onLoad();
	}

	public void loadLangs() {
		for (File f : langFolder.listFiles((dir, name) -> {
			return name.endsWith(".flang");
		})) {
			String lang = f.getName().replace(".flang", "");
			LangStorage storage = new LangStorage(new LangType(lang));
			if (storage.load(f)) {
				langs.add(lang);
				langStorages.add(storage);
			} else {
				Lang.console(LangKey.Lang.COULD_NOT_LOAD_FILE, f.getName());
			}
		}

		Lang.console(LangKey.Lang.LOAD, langStorages.size() + "");
	}

	public LangStorage getLangStorage(LangType lang) {
		if (!isLangLoaded(lang)) {
			return defaultLangStorage;
		}

		for (LangStorage storage : langStorages) {
			if (storage.getLang().key.equals(lang.key)) {
				return storage;
			}
		}
		return defaultLangStorage;
	}

	public LangStorage getDefaultLangStorage() {
		return defaultLangStorage;
	}

	public LangType getDefaultLang() {
		return defaultLang;
	}

	public void setDefaultLang(LangType defaultLang) {
		this.defaultLang = defaultLang;
	}

	public boolean isLangLoaded(LangType lang) {
		return langs.contains(lang.key);
	}

	public void setLangFolder(File langFolder) {
		this.langFolder = langFolder;
	}

	public static LangHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LangHandler();
		}
		return INSTANCE;
	}
}

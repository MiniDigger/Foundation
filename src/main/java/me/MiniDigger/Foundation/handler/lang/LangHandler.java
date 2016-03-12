package me.MiniDigger.Foundation.handler.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.MiniDigger.Foundation.FoundationMain;
import me.MiniDigger.Foundation.handler.FoundationHandler;

public class LangHandler extends FoundationHandler {
	
	private static LangHandler INSTANCE;
	
	private LangType defaultLang = new LangType("en_US");
	
	private final LangStorage defaultLangStorage = new LangStorage(defaultLang);
	private final List<LangStorage> langStorages = new ArrayList<>();
	private final List<String> langs = new ArrayList<>();
	
	private File langFolder;
	private final List<LangKey> additionalLangKeys = new ArrayList<>();
	
	@Override
	public boolean onLoad() {
		if (langFolder == null) {
			try {
				langFolder = new File(FoundationMain.getInstance().getDataFolder(), "lang");
			} catch (NullPointerException e) {
			}
		}
		loadLangs();
		return true;
	}
	
	public void loadLangs() {
		if (langFolder == null) {
			return;
		}
		
		if (!langFolder.exists()) {
			langFolder.mkdirs();
		}
		
		for (final File f : langFolder.listFiles((dir, name) -> {
			return name.endsWith(".flang");
		})) {
			final String lang = f.getName().replace(".flang", "");
			final LangStorage storage = new LangStorage(new LangType(lang));
			if (storage.load(f)) {
				langs.add(lang);
				langStorages.add(storage);
			} else {
				Lang.console(LangKey.Lang.COULD_NOT_LOAD_FILE, f.getName());
			}
		}
		
		Lang.console(LangKey.Lang.LOAD, langStorages.size() + "");
		
		if (langStorages.size() == 0) {
			defaultLangStorage.save(new File(langFolder, defaultLangStorage.getLang().key + ".flang"));
			Lang.console(LangKey.Lang.SAVE_DEFAULT, defaultLangStorage.getLang().key + ".flang");
		}
	}
	
	public LangStorage getLangStorage(final LangType lang) {
		if (!isLangLoaded(lang)) {
			return defaultLangStorage;
		}
		
		for (final LangStorage storage : langStorages) {
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
	
	public void setDefaultLang(final LangType defaultLang) {
		this.defaultLang = defaultLang;
	}
	
	public boolean isLangLoaded(final LangType lang) {
		return langs.contains(lang.key);
	}
	
	public void setLangFolder(final File langFolder) {
		this.langFolder = langFolder;
	}
	
	public static LangHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LangHandler();
		}
		return INSTANCE;
	}
	
	public List<LangKey> getAdditionalLangKeys() {
		return additionalLangKeys;
	}
	
	public void addAdditionalLangKeys(final List<LangKey> list) {
		additionalLangKeys.addAll(list);
		defaultLangStorage.load(new File(langFolder, defaultLang.key + ".flang"));
		defaultLangStorage.save(new File(langFolder, defaultLang.key + ".flang"));
	}
	
	public void removeAdditionalLangKeys(final List<LangKey> values) {
		additionalLangKeys.removeAll(values);
	}
}

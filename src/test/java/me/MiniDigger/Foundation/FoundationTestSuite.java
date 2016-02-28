package me.MiniDigger.Foundation;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import me.MiniDigger.Foundation.config.ConfigTest;
import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.command.CommandTest;
import me.MiniDigger.Foundation.handler.lang.LangTest;
import me.MiniDigger.Foundation.handler.module.ModuleTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LangTest.class, CommandTest.class, ConfigTest.class, ModuleTest.class })
public class FoundationTestSuite {
	@BeforeClass
	public static void setup() throws Exception {
		FoundationMain.setTestMode(true);
		final Field f = FoundationMain.class.getDeclaredField("handler");
		f.setAccessible(true);
		for (final FoundationHandler h : (FoundationHandler[]) f.get(null)) {
			System.out.println("--------- Load " + h.getName() + " ------------");
			h.onLoad();
		}
		for (final FoundationHandler h : (FoundationHandler[]) f.get(null)) {
			System.out.println("--------- Enable " + h.getName() + " ------------");
			h.onEnable();
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		final Field f = FoundationMain.class.getDeclaredField("handler");
		f.setAccessible(true);
		for (final FoundationHandler h : (FoundationHandler[]) f.get(null)) {
			System.out.println("--------- Disable " + h.getName() + " ------------");
			h.onDisable();
		}
	}
}

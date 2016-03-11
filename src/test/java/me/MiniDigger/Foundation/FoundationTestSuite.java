package me.MiniDigger.Foundation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import me.MiniDigger.Foundation.handler.FoundationHandler;
import me.MiniDigger.Foundation.handler.command.CommandHandler;
import me.MiniDigger.Foundation.handler.command.CommandTest;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;
import me.MiniDigger.Foundation.handler.config.ConfigTest;
import me.MiniDigger.Foundation.handler.lang.LangHandler;
import me.MiniDigger.Foundation.handler.lang.LangTest;
import me.MiniDigger.Foundation.handler.module.ModuleHandler;
import me.MiniDigger.Foundation.handler.module.ModuleTest;
import me.MiniDigger.Foundation.handler.permission.PermHolderTest;
import me.MiniDigger.Foundation.handler.permission.PermNodeTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LangTest.class, ConfigTest.class, ModuleTest.class, CommandTest.class, PermHolderTest.class,
		PermNodeTest.class })
public class FoundationTestSuite {

	private static final FoundationHandler[] handler = { LangHandler.getInstance(), ConfigHandler.getInstance(),
			ModuleHandler.getInstance(), CommandHandler.getInstance() };

	@BeforeClass
	public static void setup() throws Exception {
		FoundationMain.setTestMode(true);
		for (final FoundationHandler h : handler) {
			System.out.println("--------- Load " + h.getName() + " ------------");
			h.onLoad();
		}
		for (final FoundationHandler h : handler) {
			System.out.println("--------- Enable " + h.getName() + " ------------");
			h.onEnable();
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		for (final FoundationHandler h : handler) {
			System.out.println("--------- Disable " + h.getName() + " ------------");
			h.onDisable();
		}
	}
}

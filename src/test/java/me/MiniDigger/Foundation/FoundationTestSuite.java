package me.MiniDigger.Foundation;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import me.MiniDigger.Foundation.handler.command.CommandTest;
import me.MiniDigger.Foundation.handler.lang.LangTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LangTest.class, CommandTest.class })
public class FoundationTestSuite {
	@BeforeClass
	public static void setTestMode() {
		FoundationMain.setTestMode(true);
	}
}

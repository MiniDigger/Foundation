package me.MiniDigger.Foundation.handler.lang;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import me.MiniDigger.Foundation.FoundationMain;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LangTest {

	@BeforeClass
	public static void loadLang() {
		LangHandler.getInstance().setLangFolder(new File("src\\test\\resources\\testLangFolder"));
	}

	@Test
	public void test1Save() {
		LangHandler.getInstance().getDefaultLangStorage()
				.save(new File("src\\test\\resources\\testLangFolder\\en_US.flang"));
	}

	@Test
	public void test2Load() {
		LangHandler.getInstance().loadLangs();
	}

	@Test
	public void testConsoleWithLang() {
		Lang.console(LangKey.TEST.TEST_VARS, new LangType("test_TEST"), "1", "2", "3", "4", "5");
	}

	@Test
	public void testCorruptedLang() {
		assertEquals("ich=ich=ucg", Lang.translate(LangKey.TEST.TEST, new LangType("currupted"), ""));
	}

	@Test
	public void testLangTypeEqualWithDefaultLang() {
		assertTrue(LangType.en_US.equals(new LangType("en_US")));
	}

	@Test
	public void testLangTypeEqualWithRandomLang() {
		assertTrue(new LangType("de_DE").equals(new LangType("de_DE")));
	}

	@Test
	public void testMsg() {
		Lang.msg(FoundationMain.getTestCommandSender(), LangKey.TEST.TEST_VARS, new LangType("test_TEST"), "1", "2",
				"3", "4", "5");
	}

	@Test
	public void testTranslate() {
		assertEquals("Just a jUnit test TEST", Lang.translate(LangKey.TEST.TEST, "TEST"));
	}

	@Test
	public void testTranslateWithABunchOfVars() {
		assertEquals("Just 1 a 2 bunch 3 of 4 vars 5", Lang.translate(LangKey.TEST.TEST_VARS, "1", "2", "3", "4", "5"));
	}

	@Test
	public void testTranslateWithDefaultLang() {
		assertEquals("Just a jUnit test TEST", Lang.translate(LangKey.TEST.TEST, LangType.en_US, "TEST"));
	}

	@Test
	public void testTranslateWithTestLang() {
		assertEquals("I 1 am 2 a 3 random 4 test 5",
				Lang.translate(LangKey.TEST.TEST_VARS, new LangType("test_TEST"), "1", "2", "3", "4", "5"));
	}

	@Test
	public void testTranslateWithTooFewVars() {
		assertEquals("Just 1 a 2 bunch 3 of 4 vars %4%", Lang.translate(LangKey.TEST.TEST_VARS, "1", "2", "3", "4"));
	}

	@Test
	public void testTranslateWithTooMuchVars() {
		assertEquals("Just 1 a 2 bunch 3 of 4 vars 5",
				Lang.translate(LangKey.TEST.TEST_VARS, "1", "2", "3", "4", "5", "6"));
	}
}

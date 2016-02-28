package me.MiniDigger.Foundation.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import me.MiniDigger.Foundation.handler.config.Config;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;

public class ConfigTest {

	private final File configFolder = new File("src\\test\\resources\\testConfigFolder");

	@BeforeClass
	public static void setup() {
		System.out.println("================= Config Test ==================");
	}

	@Test
	public void testSaveAndLoad() {
		final File file = new File(configFolder, "sampleconfig.yml");
		final SampleConfig config = new SampleConfig();

		config.bool = true;
		config.integer = 9000;
		config.string = "Hello from the other side";

		ConfigHandler.getInstance().saveConfig(config, file);

		final Config c = ConfigHandler.getInstance().loadConfig(SampleConfig.class, file);
		if (c instanceof SampleConfig) {
			final SampleConfig sc = (SampleConfig) c;
			assertEquals(true, sc.bool);
			assertEquals(Integer.valueOf(9000), sc.integer);
			assertEquals("Hello from the other side", sc.string);
		} else {
			fail("Loaded config was not instanceof saved config");
		}
	}
}

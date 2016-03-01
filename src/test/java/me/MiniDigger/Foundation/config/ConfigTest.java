package me.MiniDigger.Foundation.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import me.MiniDigger.Foundation.handler.config.Config;
import me.MiniDigger.Foundation.handler.config.ConfigHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class ConfigTest {

	private final File configFolder = new File("src\\test\\resources\\testConfigFolder");

	@BeforeClass
	public static void setup() {
		System.out.println("================= Config Test ==================");
		// fake bukkit.getworld
		PowerMockito.mockStatic(Bukkit.class);
		final World world = Mockito.mock(World.class);
		Mockito.when(world.getName()).thenReturn("welt");
		Mockito.when(Bukkit.getWorld("welt")).thenReturn(world);
	}

	@Test
	public void testSaveAndLoad() {

		final File file = new File(configFolder, "sampleconfig.yml");
		final SampleConfig config = new SampleConfig();

		config.bool = true;
		config.integer = 9000;
		config.string = "Hello from the other side";
		config.test = 900;
		config.bool = true;
		config.loc = new Location(Bukkit.getWorld("welt"), 89.2, 281.5, 2.3, 25.3f, 5.5f);

		ConfigHandler.getInstance().saveConfig(config, file);

		final Config c = ConfigHandler.getInstance().loadConfig(SampleConfig.class, file);
		if (c instanceof SampleConfig) {
			final SampleConfig sc = (SampleConfig) c;
			assertEquals(true, sc.bool);
			assertEquals(Integer.valueOf(9000), sc.integer);
			assertEquals("Hello from the other side", sc.string);
			assertEquals(900, sc.test);
			assertEquals(true, sc.test2);
			assertEquals(new Location(Bukkit.getWorld("welt"), 89.2, 281.5, 2.3, 25.3f, 5.5f), sc.loc);
		} else {
			fail("Loaded config was not instanceof saved config");
		}
	}
}

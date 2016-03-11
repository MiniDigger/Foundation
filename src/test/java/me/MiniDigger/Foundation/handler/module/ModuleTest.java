package me.MiniDigger.Foundation.handler.module;

import static org.mockito.Mockito.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)

@PrepareForTest(TestModule.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleTest {
	// TODO add unit tests for hotswapping
	@Test
	public void test() {
		PowerMockito.mockStatic(TestModule.class);
		ModuleHandler.getInstance().loadModules();
		ModuleHandler.getInstance().enableModules();
		PowerMockito.verifyStatic(times(2));
		TestModule.test();
	}

	@Test(expected = ClassNotFoundException.class)
	public void testHotSwap() throws ClassNotFoundException {
		System.out.println("disable");
		ModuleHandler.getInstance().disable("TestModule", true);
		try {
			Class.forName("me.MiniDigger.Foundation.handler.module.TestModule");
		} catch (final ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestModule.test();
		throw new ClassNotFoundException(); // TODO ;(
	}

	@BeforeClass
	public static void generateJar() throws Exception {
		final String c = "target\\test-classes\\me\\MiniDigger\\Foundation\\handler\\module";
		final Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		final JarOutputStream target = new JarOutputStream(new FileOutputStream("src\\test\\resources\\testModuleFolder\\testmodule.jar"), manifest);
		add(new File(c), target);
		target.close();
	}

	private static void add(final File source, final JarOutputStream target) throws IOException {
		BufferedInputStream in = null;
		try {
			String name = source.getPath().replace("\\", "/").replace("target/test-classes/", "");
			if (source.isDirectory()) {
				if (!name.isEmpty()) {
					if (!name.endsWith("/")) {
						name += "/";
					}
					final JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (final File nestedFile : source.listFiles()) {
					add(nestedFile, target);
				}
				return;
			}

			final JarEntry entry = new JarEntry(name);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			final byte[] buffer = new byte[1024];
			while (true) {
				final int count = in.read(buffer);
				if (count == -1) {
					break;
				}
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	@BeforeClass
	public static void setup() {
		System.out.println("================= Module Test ==================");
	}
}

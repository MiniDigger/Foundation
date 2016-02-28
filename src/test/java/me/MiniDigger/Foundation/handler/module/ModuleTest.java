package me.MiniDigger.Foundation.handler.module;

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
import org.junit.Test;

public class ModuleTest {

	@Test
	public void test() {
		//TODO add unit tests for hotswapping
	}

	@BeforeClass
	public static void generateJar() throws Exception {
		String c = "target\\test-classes\\me\\MiniDigger\\Foundation\\handler\\module";
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		JarOutputStream target = new JarOutputStream(
				new FileOutputStream("src\\test\\resources\\testModuleFolder\\testmodule.jar"), manifest);
		add(new File(c), target);
		target.close();
	}

	private static void add(File source, JarOutputStream target) throws IOException {
		BufferedInputStream in = null;
		try {
			String name = source.getPath().replace("\\", "/").replace("target/test-classes/", "");
			if (source.isDirectory()) {
				if (!name.isEmpty()) {
					if (!name.endsWith("/"))
						name += "/";
					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles())
					add(nestedFile, target);
				return;
			}

			JarEntry entry = new JarEntry(name);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null)
				in.close();
		}
	}
}

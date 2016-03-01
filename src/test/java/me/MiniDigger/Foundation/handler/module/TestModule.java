package me.MiniDigger.Foundation.handler.module;

@ModuleDescription(name = "Test Module", author = "MiniDigger", version = "1.0")
public class TestModule extends Module {
	@Override
	public boolean onLoad() {
		System.out.println("I AM LOADING");
		return true;
	}

	@Override
	public boolean onEnable() {
		System.out.println("I AM ENABLEING");
		TestModule.test();
		TestModule.test();
		return true;
	}

	@Override
	public boolean onDisable() {
		System.out.println("I AM DISABLEING");
		return true;
	}

	public static void test() {
		System.out.println("test got calledr");
	}
}

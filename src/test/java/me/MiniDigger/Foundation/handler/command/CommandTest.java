package me.MiniDigger.Foundation.handler.command;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import me.MiniDigger.Foundation.handler.command.CommandDescription;
import me.MiniDigger.Foundation.handler.command.CommandHandler;
import me.MiniDigger.Foundation.handler.command.CommandWrongParamsException;
import me.MiniDigger.Foundation.handler.command.adapters.BooleanAdapter;

import org.bukkit.command.CommandSender;

public class CommandTest {

	private static CommandHandler handler;

	@BeforeClass
	public static void setup() throws CommandWrongParamsException {
		handler = new CommandHandler();
		handler.onEnable();
		handler.register(new CommandTest());
	}

	@CommandDescription(name = "test1", permission = "test1")
	public void testTest1(CommandSender sender, String s) {
		sender.sendMessage(s);
	}

	@Test
	public void testTest1() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test1")).thenReturn(true);
		handler.execute(sender, "test1 TEST_STRING");
		Mockito.verify(sender).sendMessage("TEST_STRING");
	}

	@CommandDescription(name = "test.test2", permission = "test2")
	public void testTest2(CommandSender sender, String s) {
		sender.sendMessage(s + "2");
	}

	@Test
	public void testTest2() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test2")).thenReturn(true);
		handler.execute(sender, "test test2 TEST_STRING_2");
		Mockito.verify(sender).sendMessage("TEST_STRING_22");
	}

	@CommandDescription(name = "test.test2.test3", permission = "test3")
	public void testTest3(CommandSender sender, String s) {
		sender.sendMessage(s + "3");
	}

	@Test
	public void testTest3() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test3")).thenReturn(true);
		handler.execute(sender, "test test2 test3 TEST_STRING_3");
		Mockito.verify(sender).sendMessage("TEST_STRING_33");
	}

	@CommandDescription(name = "int", permission = "int")
	public void testInt(CommandSender sender, Integer i) {
		sender.sendMessage(i + 1 + "");
	}

	@Test
	public void testInt() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("int")).thenReturn(true);
		handler.execute(sender, "int 245");
		Mockito.verify(sender).sendMessage("246");
	}

	@CommandDescription(name = "negint", permission = "negint")
	public void testNegInt(CommandSender sender, Integer i) {
		sender.sendMessage(i + 1 + "");
	}

	@Test
	public void testNegInt() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("negint")).thenReturn(true);
		handler.execute(sender, "negint -245");
		Mockito.verify(sender).sendMessage("-244");
	}

	@CommandDescription(name = "largeint", permission = "largeint")
	public void testLargeInt(CommandSender sender, Integer i) {
		sender.sendMessage(i + 1 + "");
	}

	@Test
	public void testLargeInt() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("largeint")).thenReturn(true);
		handler.execute(sender, "largeint 10000000000000000");
		Mockito.verify(sender).sendMessage("Wrong arguments: ArgIndex: 0, expected:Integer, got: 10000000000000000 (is integer to long?)");
	}

	@CommandDescription(name = "double", permission = "double")
	public void testDouble(CommandSender sender, Double i) {
		sender.sendMessage(i + 1 + "");
	}

	@Test
	public void testDouble() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double")).thenReturn(true);
		handler.execute(sender, "double 245");
		Mockito.verify(sender).sendMessage("246.0");
	}

	@Test
	public void testDouble2() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double")).thenReturn(true);
		handler.execute(sender, "double -244.4");
		Mockito.verify(sender).sendMessage("-243.4");
	}

	@Test
	public void testLongDouble() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double")).thenReturn(true);
		handler.execute(sender, "double 2.2222222222222222222222222222222222222222222222222");
		Mockito.verify(sender).sendMessage("3.2222222222222223");
	}

	@Test
	public void testDoubleWithComma() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double")).thenReturn(true);
		handler.execute(sender, "double 200,3");
		Mockito.verify(sender).sendMessage("Wrong arguments: ArgIndex: 0, expected:Double, got: 200,3 (only '.' allowed!)");
	}

	@Test
	public void testNegDouble() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double")).thenReturn(true);
		handler.execute(sender, "double -245");
		Mockito.verify(sender).sendMessage("-244.0");
	}

	@CommandDescription(name = "double.many", permission = "double.many")
	public void testManyDouble(CommandSender sender, Double i1, Double i2, Double i3, Double i4, Double i5) {
		sender.sendMessage(i1 + " " + i2 + " " + i3 + " " + i4 + " " + i5);
	}

	@Test
	public void testManyDouble() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("double.many")).thenReturn(true);
		handler.execute(sender, "double many 1.2 3.4 5.6 7.8 9.10");
		Mockito.verify(sender).sendMessage("1.2 3.4 5.6 7.8 9.1");
	}

	@Test
	public void testNoPerm() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test1")).thenReturn(false);
		handler.execute(sender, "test1 TEST_STRING");
		Mockito.verify(sender).sendMessage("No perm to perform that action");
	}

	@Test
	public void testLargeString() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test1")).thenReturn(true);
		handler.execute(sender, "test1 \"TEST STRING\"");
		Mockito.verify(sender).sendMessage("TEST STRING");
	}

	@CommandDescription(name = "boolean", permission = "boolean")
	public void testBoolean(CommandSender sender, Boolean i) {
		sender.sendMessage(i + "");
	}

	@Test
	public void testBooleanTrue() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("boolean")).thenReturn(true);
		for (String s : BooleanAdapter.trueStrings) {
			handler.execute(sender, "boolean " + s);
		}
		Mockito.verify(sender, Mockito.times(BooleanAdapter.trueStrings.length)).sendMessage("true");
	}

	@Test
	public void testBooleanFalse() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("boolean")).thenReturn(true);
		for (String s : BooleanAdapter.falseStrings) {
			handler.execute(sender, "boolean " + s);
		}
		Mockito.verify(sender, Mockito.times(BooleanAdapter.falseStrings.length)).sendMessage("false");
	}

	@Test
	public void testUnregister() throws CommandWrongParamsException {
		handler.unregister(new CommandTest());
		CommandSender sender = Mockito.mock(CommandSender.class);
		Mockito.when(sender.hasPermission("test1")).thenReturn(true);
		handler.execute(sender, "test1 TEST");
		Mockito.verify(sender).sendMessage("not handled! test1 TEST");
		handler.register(new CommandTest());
	}
}

package me.MiniDigger.Foundation.handler.command;

import org.bukkit.command.CommandSender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import me.MiniDigger.Foundation.FoundationMain;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FoundationMain.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandTest {

	private static org.bukkit.command.Command cmd;
	private static CommandSender sender;

	//TODO we need to test the completers too

	@Test
	public void testMainCommand() {
		CommandHandler.getInstance().onCommand(sender, cmd, "test", new String[0]);
		verify(sender, times(1)).sendMessage("test");
	}

	@Command(name = "test", aliases = "alias")
	public void test(CommandArgs args) {
		args.getSender().sendMessage("test");
	}

	@Test
	public void testSubCommand() {
		CommandHandler.getInstance().onCommand(sender, cmd, "test", new String[] { "test" });
		verify(sender, times(1)).sendMessage("test.test");
	}

	@Command(name = "test.test")
	public void testtest(CommandArgs args) {
		args.getSender().sendMessage("test.test");
	}

	@Test
	public void testNoPerm() {
		CommandHandler.getInstance().onCommand(sender, cmd, "test", new String[] { "test2" });
		verify(sender, times(1)).sendMessage("You do not have permission to perform that action");
	}

	@Command(name = "test.test2", permission = "noperm")
	public void testtest2(CommandArgs args) {
		args.getSender().sendMessage("you will never see me, muhahahah");
	}

	@Test
	public void testUnregister() {
		CommandHandler.getInstance().unregisterCommands(new CommandTest());

		CommandHandler.getInstance().onCommand(sender, cmd, "test", new String[0]);
		verify(sender, times(1)).sendMessage("test is not handled! Oh noes!");

		FoundationMain plugin = PowerMockito.mock(FoundationMain.class);
		when(plugin.getName()).thenReturn("Foundation");
		CommandHandler.getInstance().setPlugin(plugin);

		CommandHandler.getInstance().registerCommands(new CommandTest());
	}

	@Test
	public void testAlias() {
		CommandHandler.getInstance().onCommand(sender, cmd, "alias", new String[0]);
		verify(sender, times(2)).sendMessage("test");// 2 because testMainCommand already called it ones
	}

	@Before
	public void prepare() {
		cmd = mock(org.bukkit.command.Command.class);
	}

	@BeforeClass
	public static void setup() {
		System.out.println("=============== Command test ================");

		FoundationMain plugin = PowerMockito.mock(FoundationMain.class);
		when(plugin.getName()).thenReturn("Foundation");
		CommandHandler.getInstance().setPlugin(plugin);

		CommandHandler.getInstance().registerCommands(new CommandTest());

		sender = mock(CommandSender.class);
		when(sender.hasPermission("test")).thenReturn(true);
		when(sender.hasPermission("")).thenReturn(true);
		when(sender.hasPermission("noperm")).thenReturn(false);
		when(sender.getName()).thenReturn("TESTER");
	}
}

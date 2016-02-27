package me.MiniDigger.Foundation;

import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class TestCommandSender implements CommandSender {

	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		throw new NotImplementedException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		throw new NotImplementedException();
	}

	@Override
	public boolean hasPermission(String arg0) {
		return true;
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		throw new NotImplementedException();
	}

	@Override
	public void recalculatePermissions() {
		throw new NotImplementedException();
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public void setOp(boolean arg0) {
		throw new NotImplementedException();
	}

	@Override
	public String getName() {
		return "TestCommandSender";
	}

	@Override
	public Server getServer() {
		throw new NotImplementedException();
	}

	@Override
	public void sendMessage(String arg0) {
		System.out.println(arg0);
	}

	@Override
	public void sendMessage(String[] arg0) {
		throw new NotImplementedException();
	}

}

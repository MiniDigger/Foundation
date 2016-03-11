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
	public PermissionAttachment addAttachment(final Plugin arg0) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin arg0, final int arg1) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin arg0, final String arg1, final boolean arg2) {
		throw new NotImplementedException();
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin arg0, final String arg1, final boolean arg2, final int arg3) {
		throw new NotImplementedException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		throw new NotImplementedException();
	}

	@Override
	public boolean hasPermission(final String arg0) {
		return true;
	}

	@Override
	public boolean hasPermission(final Permission arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(final String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isPermissionSet(final Permission arg0) {
		throw new NotImplementedException();
	}

	@Override
	public void recalculatePermissions() {
		throw new NotImplementedException();
	}

	@Override
	public void removeAttachment(final PermissionAttachment arg0) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public void setOp(final boolean arg0) {
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
	public void sendMessage(final String arg0) {
		System.out.println(arg0);
	}

	@Override
	public void sendMessage(final String[] arg0) {
		throw new NotImplementedException();
	}

}

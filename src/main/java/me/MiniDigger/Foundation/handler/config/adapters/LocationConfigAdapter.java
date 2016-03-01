package me.MiniDigger.Foundation.handler.config.adapters;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.MiniDigger.Foundation.handler.config.ConfigAdapter;
import me.MiniDigger.Foundation.handler.config.ConfigAdapterNotApplicable;

public class LocationConfigAdapter extends ConfigAdapter {

	@Override
	public Class<?> getClazz() {
		return Location.class;
	}

	@Override
	public Object fromString(final String source) {
		if (source.startsWith("w=") && source.contains(",x=") && source.contains(",y=") && source.contains(",z=") && source.contains(",p=")
				&& source.contains(",yaw=")) {
			final String parts[] = source.split(",");
			final String world = parts[0].replaceFirst("w=", "");
			final double x = Double.parseDouble(parts[1].replaceFirst("x=", ""));
			final double y = Double.parseDouble(parts[2].replaceFirst("y=", ""));
			final double z = Double.parseDouble(parts[3].replaceFirst("z=", ""));
			final float p = Float.parseFloat(parts[4].replaceFirst("p=", ""));
			final float yaw = Float.parseFloat(parts[5].replaceFirst("yaw=", ""));
			return new Location(Bukkit.getWorld(world), x, y, z, yaw, p);
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

	@Override
	public String toString(final Object source) {
		if (source instanceof Location) {
			final Location loc = (Location) source;
			return "w=" + loc.getWorld().getName() + ",x=" + loc.getX() + ",y=" + loc.getY() + ",z=" + loc.getZ() + ",p=" + loc.getPitch() + ",yaw="
					+ loc.getYaw();
		}
		throw new ConfigAdapterNotApplicable(this, source);
	}

}

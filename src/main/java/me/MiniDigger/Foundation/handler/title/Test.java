package me.MiniDigger.Foundation.handler.title;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Test {
	public void test() {
		final BossBar bar = Bukkit.createBossBar("Title", BarColor.BLUE, BarStyle.SEGMENTED_20, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY,
				BarFlag.PLAY_BOSS_MUSIC);
		final Player p = null;
		p.sendTitle("", "");
	}
}

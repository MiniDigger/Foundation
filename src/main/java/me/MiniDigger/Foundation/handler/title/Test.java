package me.MiniDigger.Foundation.handler.title;

import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;

public class Test {
	public void test() {
		BossBar bar = Bukkit.createBossBar("Title", BarColor.BLUE, BarStyle.SEGMENTED_20, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY, BarFlag.PLAY_BOSS_MUSIC);
		Player p = null;
		p.sendTitle("", "");
	}
}

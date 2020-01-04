package cz.radimsuckr.spigot_faster_night;

import java.util.List;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	private final long BEDTIME_START = 12542;
	private final String WORLD_NAME = "world";

	@Override
	public void onEnable() {
		World world = Bukkit.getWorlds().stream()
			.filter(w -> w.getName().equals(WORLD_NAME))
			.findFirst()
			.orElseThrow(() -> new WorldNotFoundException(String.format("Cannot find world named \"%s\"", WORLD_NAME)));

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				long time = world.getTime();
				long playersInBedCount = world.getPlayers().stream().filter(p -> p.isSleeping()).count();

				if (time > BEDTIME_START) {
					world.setFullTime(world.getFullTime() + playersInBedCount);
				}
			}
		}, 0, 1);
	}
}

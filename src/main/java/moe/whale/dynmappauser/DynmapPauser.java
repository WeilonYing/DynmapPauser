package moe.whale.dynmappauser;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DynmapPauser extends JavaPlugin {
    private boolean isPaused = false;
    private int playerCountLimit = 1;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        playerCountLimit = getConfig().getInt("player-count-limit");

        BukkitRunnable pauserRunnable = new BukkitRunnable() {
            public void run() {
                int onlinePlayers = getServer().getOnlinePlayers().size();
                if (onlinePlayers > playerCountLimit && !isPaused) {
                    runServerCommand("dynmap pause all"); // pause dynmap rendering
                    getLogger().info("Dynmap rendering has been paused");
                    isPaused = true;
                } else if (isPaused) {
                    runServerCommand("dynmap pause none"); // unpause dynmap rendering
                    getLogger().info("Dynmap rendering has been unpaused");
                    isPaused = false;
                }
            }
        };

        pauserRunnable.runTaskTimer(this, 400, 400);
    }

    @Override
    public void onDisable() {
        // nothing to do here
    }

    private void runServerCommand(String command) {
        getServer().dispatchCommand(
                getServer().getConsoleSender(),
                command);
    }
}

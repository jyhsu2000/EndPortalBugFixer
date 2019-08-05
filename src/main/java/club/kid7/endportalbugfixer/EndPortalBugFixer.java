package club.kid7.endportalbugfixer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndPortalBugFixer extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
}

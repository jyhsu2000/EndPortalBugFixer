package club.kid7.endportalbugfixer;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndPortalBugFixer extends JavaPlugin implements Listener {
  private List<String> forbidden;

  // -- JavaPlugin methods --------------------------------------------------------------------------------------------

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("EndPortalBugFixer-reload")) {
      reloadConfig();
      sender.sendMessage("§a[EndPortalBugFixer] Configuration reloaded.");
      return true;
    }

    return super.onCommand(sender, command, label, args);
  }

  @Override
  public void onDisable() {
    super.onDisable();
    HandlerList.unregisterAll((JavaPlugin) this);
  }

  @Override
  public void onEnable() {
    reloadConfig();
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @Override
  public void reloadConfig() {
    super.reloadConfig();

    saveDefaultConfig();
    getConfig().options().copyDefaults(true);
    saveConfig();

    forbidden = getConfig().getStringList("forbidden");
  }

  // -- Listener methods ----------------------------------------------------------------------------------------------

  @EventHandler
  void onPlayerInteract(PlayerInteractEvent event) {
    if (event.isCancelled() || event.getClickedBlock() == null || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        || event.getPlayer().hasPermission("EndPortalBugFixer.bypass")
        || !event.getClickedBlock().getRelative(event.getBlockFace()).getType().equals(Material.END_PORTAL))
      return;

    ItemStack item = event.getItem();

    if (item != null && forbidden.contains(item.getType().toString())) event.setCancelled(true);
  }
}

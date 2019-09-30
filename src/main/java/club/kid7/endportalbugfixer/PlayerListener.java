package club.kid7.endportalbugfixer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    void onPlayerInteract(PlayerInteractEvent event) {
        //右鍵點擊方塊
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getClickedBlock() == null) {
            return;
        }

        //使用能夠倒出液體的桶子
        ItemStack item = event.getItem();
        if (item != null
            && !item.getType().equals(Material.WATER_BUCKET)
            && !item.getType().equals(Material.LAVA_BUCKET)
            && !item.getType().equals(Material.PUFFERFISH_BUCKET)
            && !item.getType().equals(Material.SALMON_BUCKET)
            && !item.getType().equals(Material.COD_BUCKET)
            && !item.getType().equals(Material.TROPICAL_FISH_BUCKET)
        ) {
            return;
        }

        //相鄰方塊（被放置液體的方塊）為終界傳送門
        Block adjacentBlock = event.getClickedBlock().getRelative(event.getBlockFace());
        if (!adjacentBlock.getType().equals(Material.END_PORTAL)) {
            return;
        }

        //取消事件
        event.setCancelled(true);
    }
}

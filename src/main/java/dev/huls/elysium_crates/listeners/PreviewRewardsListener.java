package dev.huls.elysium_crates.listeners;

import dev.huls.elysium_crates.methods.BoxMethod;
import dev.huls.elysium_crates.methods.RandomRewardMethod;
import dev.huls.elysium_crates.methods.InventoryMethod;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PreviewRewardsListener implements Listener {

    private final InventoryMethod inventoryMethod;

    public PreviewRewardsListener(InventoryMethod inventoryMethod) {
        this.inventoryMethod = inventoryMethod;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE) return;
        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        if (meta != null && meta.getDisplayName().equals("§e§lCHAVE DE REMOÇÃO")) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (Objects.requireNonNull(event.getClickedBlock()).getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);

                BoxMethod boxMethod = BoxManagerListener.getBox(Objects.requireNonNull(event.getClickedBlock()).getLocation().toVector());
                if (boxMethod != null) {
                    RandomRewardMethod randomReward;
                    int startSlot = 10;
                    Player player = event.getPlayer();
                    switch (boxMethod.getType()) {
                        case BASIC:
                            InventoryMethod.openBasicRewardsInventory(player, startSlot);
                            event.getPlayer().sendMessage("§ePré-visualizando recompensas básicas...");
                            break;
                        case MEDIUM:
                            InventoryMethod.openMediumRewardsInventory(player, startSlot);
                            event.getPlayer().sendMessage("§ePré-visualizando recompensas médias...");
                            break;
                        case ADVANCED:
                            InventoryMethod.openAdvancedRewardsInventory(player, startSlot);
                            event.getPlayer().sendMessage("§ePré-visualizando recompensas avançadas...");
                            break;
                        default:
                            return;
                    }
                }
            }
        }
    }
}
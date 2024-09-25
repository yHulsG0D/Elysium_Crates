package dev.huls.elysium_crates.methods;

import dev.huls.elysium_crates.items.RewardSetup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryMethod {

    private static RewardSetup rewardSetup;

    public InventoryMethod(RewardSetup rewardSetup) {
        InventoryMethod.rewardSetup = rewardSetup;
    }

    public static void openBasicRewardsInventory(Player player, int startSlot) {
        Inventory inventory = createInventory("Recompensas Básicas", rewardSetup.getBasicRewards(), startSlot);
        player.openInventory(inventory);
    }

    public static void openMediumRewardsInventory(Player player, int startSlot) {
        Inventory inventory = createInventory("Recompensas Médias", rewardSetup.getMediumRewards(), startSlot);
        player.openInventory(inventory);
    }

    public static void openAdvancedRewardsInventory(Player player, int startSlot) {
        Inventory inventory = createInventory("Recompensas Avançadas", rewardSetup.getAdvancedRewards(), startSlot);
        player.openInventory(inventory);
    }

    private static Inventory createInventory(String title, List<RewardMethod> rewardMethods, int startSlot) {
        Inventory inventory = Bukkit.createInventory(null, 36, title);

        int slot = startSlot;
        for (RewardMethod rewardMethod : rewardMethods) {
            if (slot >= inventory.getSize()) break;

            ItemStack item = rewardMethod.getItemStack().clone();
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GREEN + rewardMethod.getName());

                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.YELLOW + "Chance: " + rewardMethod.getChance() + "%");
                meta.setLore(lore);

                item.setItemMeta(meta);
            }
            inventory.setItem(slot, item);
            slot++;
        }

        return inventory;
    }
}
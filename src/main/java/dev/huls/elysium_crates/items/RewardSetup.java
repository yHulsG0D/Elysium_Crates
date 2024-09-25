package dev.huls.elysium_crates.items;

import dev.huls.elysium_crates.methods.RewardMethod;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RewardSetup {

    public List<RewardMethod> getBasicRewards() {
        List<RewardMethod> rewardMethods = new ArrayList<>();
        rewardMethods.add(new RewardMethod(new ItemStack(Material.COAL, 1), 30.0, "Carvão"));
        rewardMethods.add(new RewardMethod(new ItemStack(Material.STONE, 1), 50.0, "Pedra"));
        return rewardMethods;
    }

    public List<RewardMethod> getMediumRewards() {
        List<RewardMethod> rewardMethods = new ArrayList<>();
        rewardMethods.add(new RewardMethod(new ItemStack(Material.IRON_INGOT, 1), 30.0, "Ferro"));
        rewardMethods.add(new RewardMethod(new ItemStack(Material.LAPIS_LAZULI, 1), 50.0, "Lápis-Lazuli"));
        return rewardMethods;
    }

    public List<RewardMethod> getAdvancedRewards() {
        List<RewardMethod> rewardMethods = new ArrayList<>();
        rewardMethods.add(new RewardMethod(new ItemStack(Material.GOLD_INGOT, 1), 50.0, "Ouro"));
        rewardMethods.add(new RewardMethod(new ItemStack(Material.EMERALD, 1), 30.0, "Esmeralda"));
        return rewardMethods;
    }
}
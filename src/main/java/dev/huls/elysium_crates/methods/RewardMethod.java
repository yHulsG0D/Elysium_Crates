package dev.huls.elysium_crates.methods;

import org.bukkit.inventory.ItemStack;

public class RewardMethod {
    private final ItemStack itemStack;
    private final double chance;
    private final String name;

    public RewardMethod(ItemStack itemStack, double chance, String name) {
        this.itemStack = itemStack;
        this.chance = chance;
        this.name = name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }
    public String getName() {
        return name;
    }
}
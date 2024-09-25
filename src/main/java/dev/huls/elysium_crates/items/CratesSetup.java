package dev.huls.elysium_crates.items;

import dev.huls.elysium_crates.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CratesSetup {

    public static ItemStack cratebasic = new ItemBuilder(Material.ENDER_CHEST)
            .name("Caixa Básica")
            .lore("§7Clique com o botão direito")
            .lore("§7para colocar a caixa.")
            .build();

    public static ItemStack cratemedia = new ItemBuilder(Material.ENDER_CHEST)
            .name("Caixa Média")
            .lore("§7Clique com o botão direito")
            .lore("§7para colocar a caixa.")
            .build();

    public static ItemStack crateavancada = new ItemBuilder(Material.ENDER_CHEST)
            .name("Caixa Avançada")
            .lore("§7Clique com o botão direito")
            .lore("§7para colocar a caixa.")
            .build();
}

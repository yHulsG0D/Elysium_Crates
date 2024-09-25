package dev.huls.elysium_crates.items;

import dev.huls.elysium_crates.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CrateRemoveSetup {

    public static ItemStack removecrate = new ItemBuilder(Material.GOLDEN_PICKAXE)
            .name("§e§lCHAVE DE REMOÇÃO")
            .lore("§7Utilize esta chave em uma")
            .lore("§7caixa para removê-la.")
            .enchantment(Enchantment.DURABILITY, 10)
            .build();
}

package dev.huls.elysium_crates.items;

import dev.huls.elysium_crates.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KeysSetup {

    public static ItemStack keyBasic = new ItemBuilder(Material.TRIPWIRE_HOOK)
            .name("§e§lCHAVE BÁSICA")
            .lore("§7Utilize este item para")
            .lore("§7abrir uma caixa básica.")
            .build();

    public static ItemStack keyMedium= new ItemBuilder(Material.TRIPWIRE_HOOK)
            .name("§e§lCHAVE MÉDIA")
            .lore("§7Utilize este item para")
            .lore("§7abrir uma caixa média.")
            .build();

    public static ItemStack keyAdvanced = new ItemBuilder(Material.TRIPWIRE_HOOK)
            .name("§e§lCHAVE AVANÇADA")
            .lore("§7Utilize este item para")
            .lore("§7abrir uma caixa avançada.")
            .build();
}
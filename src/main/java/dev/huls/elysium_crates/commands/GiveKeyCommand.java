package dev.huls.elysium_crates.commands;

import dev.huls.elysium_crates.items.KeysSetup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveKeyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§cUso correto: /givekey <quantidade>");
            return true;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cA quantidade deve ser um número inteiro.");
            return true;
        }

        if (quantidade < 1) {
            player.sendMessage("§cA quantidade deve ser maior que zero.");
            return true;
        }

        // Criar uma cópia do item com a quantidade especificada
        ItemStack itemsToGive = KeysSetup.keyBasic.clone();
        itemsToGive.setAmount(quantidade);

        ItemStack itemsToGive2 = KeysSetup.keyMedium.clone();
        itemsToGive2.setAmount(quantidade);

        ItemStack itemsToGive3 = KeysSetup.keyAdvanced.clone();
        itemsToGive3.setAmount(quantidade);

        // Dar o item ao jogador
        player.getInventory().addItem(itemsToGive);
        player.getInventory().addItem(itemsToGive2);
        player.getInventory().addItem(itemsToGive3);
        player.sendMessage("§eVocê recebeu §e" + quantidade + " §echaves básicas!");
        return true;
    }
}
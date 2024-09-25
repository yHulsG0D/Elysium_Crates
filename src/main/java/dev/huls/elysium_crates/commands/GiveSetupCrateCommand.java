package dev.huls.elysium_crates.commands;

import dev.huls.elysium_crates.items.CratesSetup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveSetupCrateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§cUso correto: /givecrate <quantidade>");
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
        ItemStack itemsToGive = CratesSetup.cratebasic.clone();
        itemsToGive.setAmount(quantidade);

        ItemStack itemsToGive2 = CratesSetup.cratemedia.clone();
        itemsToGive.setAmount(quantidade);

        ItemStack itemsToGive3 = CratesSetup.crateavancada.clone();
        itemsToGive.setAmount(quantidade);

        // Dar o item ao jogador
        player.getInventory().addItem(itemsToGive);
        player.getInventory().addItem(itemsToGive2);
        player.getInventory().addItem(itemsToGive3);
        player.sendMessage("§eVocê recebeu §e" + quantidade + " §ecaixas básicas!");
        return true;
    }
}
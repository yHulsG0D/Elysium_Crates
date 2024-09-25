package dev.huls.elysium_crates.commands;

import dev.huls.elysium_crates.items.CrateRemoveSetup;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class GiveRemoveItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§cUso correto: /giveremoveitem <player>");
            return true;
        }
        String playerName = args[0];
        Player playertarget = Bukkit.getPlayer(playerName);

        if (playertarget != null && playertarget.isOnline()) {
            player.sendMessage("§eVocê deu um item de remoção de caixa para " + playerName + "§e!");
            CrateRemoveSetup.removecrate.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            CrateRemoveSetup.removecrate.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            CrateRemoveSetup.removecrate.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            playertarget.getInventory().addItem(CrateRemoveSetup.removecrate);
        } else {
            player.sendMessage("§cO jogador com o nome " + playerName + " §cnão existe ou está offline.");
        }
        return true;
    }
}

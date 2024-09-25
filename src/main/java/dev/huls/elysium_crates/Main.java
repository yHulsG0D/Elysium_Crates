package dev.huls.elysium_crates;

import dev.huls.elysium_crates.commands.GiveKeyCommand;
import dev.huls.elysium_crates.commands.GiveRemoveItemCommand;
import dev.huls.elysium_crates.commands.GiveSetupCrateCommand;
import dev.huls.elysium_crates.items.RewardSetup;
import dev.huls.elysium_crates.listeners.InventoryClickListener;
import dev.huls.elysium_crates.listeners.PreviewRewardsListener;
import dev.huls.elysium_crates.listeners.BoxManagerListener;
import dev.huls.elysium_crates.methods.InventoryMethod;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private BoxManagerListener boxManagerListener;

    @Override
    public void onEnable() {
        boxManagerListener = new BoxManagerListener(this);
        RewardSetup rewardSetup = new RewardSetup();
        InventoryMethod inventoryMethod = new InventoryMethod(rewardSetup);
        PreviewRewardsListener boxClickListener = new PreviewRewardsListener(inventoryMethod);
        getServer().getPluginManager().registerEvents(boxClickListener, this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);


        getServer().getPluginManager().registerEvents(boxManagerListener, this);
        Objects.requireNonNull(getCommand("givekey")).setExecutor(new GiveKeyCommand());
        Objects.requireNonNull(getCommand("givecrate")).setExecutor(new GiveSetupCrateCommand());
        Objects.requireNonNull(getCommand("giveremoveitem")).setExecutor(new GiveRemoveItemCommand());
        boxManagerListener.loadBoxes();
    }
    @Override
    public void onDisable() {
        boxManagerListener.saveBoxes();
    }
    public BoxManagerListener getBoxManager() {
        return boxManagerListener;
    }
}

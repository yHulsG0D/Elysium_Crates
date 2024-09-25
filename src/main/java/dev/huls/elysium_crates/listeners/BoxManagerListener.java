package dev.huls.elysium_crates.listeners;

import dev.huls.elysium_crates.enums.BoxTypeEnum;
import dev.huls.elysium_crates.items.KeysSetup;
import dev.huls.elysium_crates.items.RewardSetup;
import dev.huls.elysium_crates.methods.BoxMethod;
import dev.huls.elysium_crates.methods.RandomRewardMethod;
import dev.huls.elysium_crates.methods.RewardMethod;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.*;

public class BoxManagerListener implements Listener {

    private final Plugin plugin;
    private static final List<BoxMethod> BOX_METHODS = new ArrayList<>();
    private final RewardSetup rewardSetup = new RewardSetup();

    public BoxManagerListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.getType() == Material.ENDER_CHEST) {
            BoxTypeEnum type = null;
            if (item.getItemMeta().hasDisplayName()) {
                String displayName = item.getItemMeta().getDisplayName();
                if (displayName.equals("Caixa Básica")) {
                    type = BoxTypeEnum.BASIC;
                } else if (displayName.equals("Caixa Média")) {
                    type = BoxTypeEnum.MEDIUM;
                } else if (displayName.equals("Caixa Avançada")) {
                    type = BoxTypeEnum.ADVANCED;
                }
            }
            if (type != null) {
                BOX_METHODS.add(new BoxMethod(event.getBlockPlaced().getLocation(), type));
                event.getPlayer().sendMessage(ChatColor.GREEN + "Você colocou uma caixa " + type.name() + "!");
                saveBoxes();

                if (DHAPI.getHologram(type.name() + "-crate") == null) {
                    String linha1 = "§5§lCAIXA " + type.name();
                    String linha2 = "§dClique direito para abrir.";
                    ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK);
                    DHAPI.createHologram(type.name() + "-crate", event.getBlockPlaced().getLocation().add(0.5, 2.0, 0.5), true, Arrays.asList(linha1, linha2));
                    DHAPI.addHologramLine(Hologram.getCachedHologram(type.name() + "-crate"), itemStack);
                } else {
                    event.getPlayer().sendMessage("§c§lERRO! §cHolograma não criado, pois já existe!");
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        BoxMethod boxMethod = getBox(event.getBlock().getLocation().toVector());
        if (boxMethod != null) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE) {
                ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                if (meta != null && meta.getDisplayName().equals("§e§lCHAVE DE REMOÇÃO")) {
                    event.setCancelled(false);
                    BOX_METHODS.remove(boxMethod);
                    event.getPlayer().sendMessage(ChatColor.RED + "Você removeu uma caixa " + boxMethod.getType().name() + "!");
                    DHAPI.removeHologram(boxMethod.getType().name() + "-crate");
                    saveBoxes();
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§cVocê precisa do item correto para remover a caixa!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Objects.requireNonNull(event.getClickedBlock()).getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);

                if (!(event.getHand() == EquipmentSlot.HAND)) return;
                ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

                BoxMethod boxMethod = getBox(Objects.requireNonNull(event.getClickedBlock()).getLocation().toVector());
                if (boxMethod != null) {
                    RandomRewardMethod randomReward;
                    if (event.getItem() != null) {
                        ItemMeta meta = itemInHand.getItemMeta();
                        switch (boxMethod.getType()) {
                            case BASIC:
                                if (itemInHand.getType() == Material.TRIPWIRE_HOOK && meta.getDisplayName().equalsIgnoreCase("§e§lCHAVE BÁSICA")) {
                                    randomReward = new RandomRewardMethod(rewardSetup.getBasicRewards());
                                    RewardMethod rewardbasic = randomReward.getRandomReward();
                                    event.getPlayer().getInventory().addItem(rewardbasic.getItemStack());
                                    event.getPlayer().getInventory().removeItem(KeysSetup.keyBasic);
                                    event.getPlayer().sendMessage("§5§lGG! §dVocê recebeu uma recompensa básica! §8(" + rewardbasic.getName() + "§8)");
                                    break;
                                } else {
                                    event.getPlayer().sendMessage("§cVocê precisa de uma chave básica para abrir essa caixa!");
                                    return;
                                }
                            case MEDIUM:
                                if (itemInHand.getType() == Material.TRIPWIRE_HOOK && meta.getDisplayName().equalsIgnoreCase("§e§lCHAVE MÉDIA")) {
                                    randomReward = new RandomRewardMethod(rewardSetup.getMediumRewards());
                                    event.getPlayer().getInventory().addItem(randomReward.getRandomReward().getItemStack());
                                    RewardMethod rewardmedium = randomReward.getRandomReward();
                                    event.getPlayer().getInventory().removeItem(KeysSetup.keyMedium);
                                    event.getPlayer().sendMessage("§5§lGG! §dVocê recebeu uma recompensa média! §8(" + rewardmedium.getName() + "§8)");
                                    break;
                                } else {
                                    event.getPlayer().sendMessage("§cVocê precisa de uma chave média para abrir essa caixa!");
                                    return;
                                }
                            case ADVANCED:
                                if (itemInHand.getType() == Material.TRIPWIRE_HOOK && meta.getDisplayName().equalsIgnoreCase("§e§lCHAVE AVANÇADA")) {
                                    randomReward = new RandomRewardMethod(rewardSetup.getAdvancedRewards());
                                    event.getPlayer().getInventory().addItem(randomReward.getRandomReward().getItemStack());
                                    RewardMethod rewardadvanced = randomReward.getRandomReward();
                                    event.getPlayer().getInventory().removeItem(KeysSetup.keyAdvanced);
                                    event.getPlayer().sendMessage("§5§lGG! §dVocê recebeu uma recompensa avançada! §8(" + rewardadvanced.getName() + "§8)");
                                    break;
                                } else {
                                    event.getPlayer().sendMessage("§cVocê precisa de uma chave avançada para abrir essa caixa!");
                                    return;
                                }
                            default:
                                return;
                        }
                    }
                }
            }
        }
    }


    public static BoxMethod getBox(Vector position) {
        for (BoxMethod boxMethod : BOX_METHODS) {
            if (boxMethod.getPosition().equals(position)) {
                return boxMethod;
            }
        }
        return null;
    }

    public void saveBoxes() {
        FileConfiguration config = plugin.getConfig();
        List<Map<String, Object>> serializedBoxes = new ArrayList<>();
        for (BoxMethod boxMethod : BOX_METHODS) {
            serializedBoxes.add(boxMethod.serialize());
        }
        config.set("boxes", serializedBoxes);
        plugin.saveConfig();
    }

    public void loadBoxes() {
        FileConfiguration config = plugin.getConfig();
        List<?> rawList = config.getList("boxes");
        if (rawList != null) {
            for (Object item : rawList) {
                if (item instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> serializedBox = (Map<String, Object>) item;
                    BOX_METHODS.add(BoxMethod.deserialize(serializedBox));
                    Bukkit.getConsoleSender().sendMessage("§a[Diverland_Crates] §aCaixas carregadas com sucesso:" + BOX_METHODS.size());
                }
            }
        }
    }
}

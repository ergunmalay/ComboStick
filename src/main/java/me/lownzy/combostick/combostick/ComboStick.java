package me.lownzy.combostick.combostick;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class ComboStick extends JavaPlugin implements Listener {

    private Map<String, String> playerOrder = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();

        if (!item.getType().equals(Material.STICK)) {
            return;
        }

        if (hand.equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        String order = playerOrder.getOrDefault(player.getName(), "");
        if (event.getAction().name().contains("RIGHT")) {
            order += "R";
            ChatColor ActionBarTextColor = ChatColor.DARK_GRAY;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(order));


        } else if (event.getAction().name().contains("LEFT")) {
            order += "L";
            ChatColor ActionBarTextColor = ChatColor.DARK_GRAY;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(order));
        }

        playerOrder.put(player.getName(), order);

        if (order.length() < 3) {
            return;
        }

        switch (order) {
            case "RLL":
                player.sendMessage(ChatColor.AQUA + "You have clicked the stick in the order RLL.");
                break;
            case "RLR":
                player.sendMessage(ChatColor.GREEN + "You have clicked the stick in the order RLR.");
                break;
            case "RRR":
                player.sendMessage(ChatColor.RED + "You have clicked the stick in the order RRR.");
                break;
            case "RRL":
                player.sendMessage(ChatColor.RED + "You have clicked the stick in the order RRL.");
                break;
            default:
                player.sendMessage(ChatColor.GOLD + "Invalid stick click sequence.");
                break;
        }

        playerOrder.remove(player.getName());
    }
}
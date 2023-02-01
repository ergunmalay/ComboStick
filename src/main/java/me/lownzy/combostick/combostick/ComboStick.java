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

    //Event handler for player interact event
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        //Get the player who interacted
        Player player = event.getPlayer();
        //Get the item the player interacted with
        ItemStack item = event.getItem();
        //Get the hand the player interacted with
        EquipmentSlot hand = event.getHand();

        //Get the order of clicks for the current player
        String order = playerOrder.getOrDefault(player.getName(), "");

        //Check if the item is not a stick, return if it is not
        if (!item.getType().equals(Material.STICK)) {
            return;
        }

        //Check if the player interacted with their off hand, return if they did
        if (hand.equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        //Check if the order is empty and the player performed a left-click, return if they did
        if (order.isEmpty() && event.getAction().name().contains("LEFT")) {
            return;
        }

        //If the player performed a right-click
        if (event.getAction().name().contains("RIGHT")) {
            //Append R to the order
            order += "R";
            //Send the order as a message in the action bar
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(order));

            //If the player performed a left-click
        } else if (event.getAction().name().contains("LEFT")) {
            //Append L to the order
            order += "L";
            //Send the order as a message in the action bar
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(order));
        }

        //Save the updated order for the current player
        playerOrder.put(player.getName(), order);

        //Check if the order is less than 3 clicks, return if it is
        if (order.length() < 3) {
            return;
        }

        //Check the order and send appropriate message to the player
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

        //Remove the order for the current player
        playerOrder.remove(player.getName());
    }
}
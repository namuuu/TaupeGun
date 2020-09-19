package fr.namu.tg.util.stuff;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.ScenarioTG;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameStuff {

    private MainTG main;

    public HashMap<Integer, ItemStack> itemStarts = new HashMap<>();
    public List<ItemStack> itemStart = new ArrayList<>();
    public ItemStack[] armorStart;

    public GameStuff(MainTG main) {
        this.main = main;
    }

    public void set(Player player) {
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0);
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setGameMode(GameMode.SURVIVAL);

        give(player);

        if(ScenarioTG.DIAMONDLIMIT.getNumber() != 0) {
            ptg.setDiamondleft(ScenarioTG.DIAMONDLIMIT.getNumber());
        }

        for (PotionEffect po : player.getActivePotionEffects())
            player.removePotionEffect(po.getType());
        if(ScenarioTG.CAT_EYES.getValue())
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        if(ScenarioTG.MASTERLEVEL.getNumber() != 0) {
            player.setLevel(ScenarioTG.MASTERLEVEL.getNumber());
        }
    }

    public void give(Player player) {
        Inventory inv = player.getInventory();

        for(Integer ind : itemStarts.keySet()) {
           ItemStack is = itemStarts.get(ind);
           inv.setItem(ind, is);
        }

        player.getInventory().setArmorContents(armorStart);
    }

    public void save(Player player) {
        Inventory inv = player.getInventory();
        Integer slot = 0;

        for(ItemStack item : inv.getContents()) {
            if(item != null) {
                itemStarts.put(slot, item);
            }
            slot++;
        }

        armorStart = player.getInventory().getArmorContents();
    }

    public void edit(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        give(player);

        TextComponent msg = new TextComponent("§aCliquez ici pour valider l'inventaire de départ !");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h startStuff"));
        player.sendMessage(" ");
        player.spigot().sendMessage(msg);
        player.sendMessage(" ");

    }
}

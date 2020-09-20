package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    private MainTG main;

    public InteractEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(this.main.info.getState().equals(StateTG.LOBBY) && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
            return;
        }
        if(!this.main.info.getState().equals(StateTG.LOBBY)) {
            return;
        }

        ItemStack current = event.getItem();
        Player player = event.getPlayer();

        if(current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }

        switch (current.getItemMeta().getDisplayName()) {
            case "§eChoisir une équipe":
                event.setCancelled(true);
                this.main.menu.teamselect.open(player);
                break;
            case "§eMenu du Host":
                event.setCancelled(true);
                this.main.menu.hostmain.open(player);
                break;
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(this.main.info.getState().equals(StateTG.LOBBY))
            event.setCancelled(true);
    }
}

package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    private MainTG main;

    public ClickEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (this.main.info.getState().equals(StateTG.LOBBY)) {
            if (inv.getName() == "container.crafting") {
                event.setCancelled(true);
                return;
            }
        }

        if(!this.main.info.getState().equals(StateTG.LOBBY)) {
            return;
        }

        if (inv.getName().contains("container")) {
            return;
        }
        if (current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }

        String invname = inv.getName();
        Material mat = current.getType();
        String currentName = current.getItemMeta().getDisplayName();
        ClickType click = event.getClick();

        System.out.println(currentName);

        switch (invname) {
            case "§7Choisis ton Équipe !":
                event.setCancelled(true);
                this.main.menu.teamselect.click(player, currentName);
                break;
            case "§7Paramètres de la partie":
                event.setCancelled(true);
                switch (currentName) {
                    case "§eConfigurer les Équipes":
                        this.main.menu.teamEdit.open(player);
                        break;
                    case "§aCommencer la Partie !":
                        this.main.start.start();
                        break;
                    case "§eConfigurer le Stuff de Départ":
                        this.main.gamestuff.edit(player);
                        player.closeInventory();
                        break;
                    case "§eConfigurer les Scénarios":
                        this.main.menu.scenarioMenu.open(player);
                        break;
                    case "§eConfigurer les Timers":
                        this.main.menu.timerMenu.open(player);
                        break;
                }
                break;
            case "§7Configuration des Scénarios":
                event.setCancelled(true);
                this.main.menu.scenarioMenu.click(player, currentName, click);
                break;
            case "§7Configuration des Équipes":
                event.setCancelled(true);
                this.main.menu.teamEdit.click(player, currentName, click);
                break;
            case "§7Configuration des Timers":
                event.setCancelled(true);
                this.main.menu.timerMenu.click(player, currentName, click);
                break;
        }
    }
}

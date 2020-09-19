package fr.namu.tg.menu;

import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HostMainMenu {

    public HostMainMenu(MenuTG menu) {
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Paramètres de la partie");

        inv.setItem(0, new ItemBuilder(Material.BANNER, 1).setName("§eConfigurer les Équipes").setDyeColor(DyeColor.RED).toItemStack());
        inv.setItem(1, new ItemBuilder(Material.CHEST, 1).setName("§eConfigurer le Stuff de Départ").toItemStack());
        inv.setItem(2, new ItemBuilder(Material.MAP, 1).setName("§eConfigurer les Scénarios").toItemStack());
        inv.setItem(3, new ItemBuilder(Material.WATCH, 1).setName("§eConfigurer les Timers").toItemStack());

        inv.setItem(49, new ItemBuilder(Material.EMERALD_BLOCK, 1).setName("§aCommencer la Partie !").toItemStack());

        player.openInventory(inv);
    }
}

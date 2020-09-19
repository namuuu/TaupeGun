package fr.namu.tg.menu;

import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class ScenarioMenu {

    public ScenarioMenu(MenuTG menu) {

    }


    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Scénarios");

        int slot = 1;
        int line = 1;

        for(ScenarioTG scenario : ScenarioTG.values()) {
            inv.setItem(slot + line * 9, scenario.itemDisplay());
            slot = slot + 1;
            if(slot >= 8) {
                line = line + 1;
                slot = 1;
            }
        }

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBuilder.glassPane(DyeColor.RED));

        player.openInventory(inv);
    }

    public void click(Player player, String itemName, ClickType click) {
        for(ScenarioTG scenario : ScenarioTG.values()) {
            if(itemName.contains(scenario.getName())) {
                scenario.switchValue(click);
            }
        }
        open(player);
    }
}

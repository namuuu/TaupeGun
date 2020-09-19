package fr.namu.tg.menu;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.MoleConfigTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class TeamEditMenu {

    private MenuTG menu;


    public TeamEditMenu(MenuTG menu) {
        this.menu = menu;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Équipes");

        int slot = 1;
        int line = 1;

        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe())
            inv.setItem(slot + line * 9, team.itemDisplay());
            slot = slot + 1;
            if(slot >= 8) {
                line = line + 1;
                slot = 1;
            }
        }

        inv.setItem(48, new ItemBuilder(Material.SLIME_BALL, 1).setName(MoleConfigTG.TEAM_OF_MOLES.getName() + "§7: §c" + MoleConfigTG.TEAM_OF_MOLES.getValue()).toItemStack());
        inv.setItem(50, new ItemBuilder(Material.MAGMA_CREAM, 1).setName(MoleConfigTG.MOLE_PER_TEAM.getName() + "§7: §c" + MoleConfigTG.MOLE_PER_TEAM.getValue()).toItemStack());


        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,49,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBuilder.glassPane(DyeColor.RED));

        player.openInventory(inv);
    }

    public void click(Player player, String itemName, ClickType click) {
        for(TeamTG team : TeamTG.values()) {
            if(itemName.contains(team.getName())) {
                team.click(click);
            }

            if(team.getSize() < team.getPlayers().size()) {
                Player get = team.getPlayers().get(0);
                this.menu.main.team.addPlayer(get, TeamTG.NULL);
            }
        }
        for(MoleConfigTG config : MoleConfigTG.values()) {
            if(itemName.contains(config.getName())) {
                config.click(click);
            }
        }

        open(player);
    }
}

package fr.namu.tg.menu;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamSelectMenu {

    private MenuTG menu;
    private MainTG main;

    public TeamSelectMenu(MenuTG menu) {
        this.menu = menu;
        this.main = menu.main;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Choisis ton Équipe !");
        Integer slot = 0;

        for(TeamTG team : TeamTG.values()) {
            if(team != TeamTG.NULL && !team.getTaupe()) {
                inv.setItem(slot, ItemBuilder.teamBannerMenu(team));
                slot++;
            }
        }

        inv.setItem(52, new ItemBuilder(Material.BARRIER, 1).setName("§eNe choisir aucune équipe").toItemStack());
        inv.setItem(53, new ItemBuilder(Material.GLASS, 1).setName("§7Rejoindre les Spectateurs (Non fonctionnel)").toItemStack());

        /*int[] SlotWhiteGlass = {
                3,4,5,11,15,19,25,27,35,36,44,46,47,48,50,51,52 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBui
        int[] SlotGreyGlass = {
                0, 1, 2, 6, 7, 8, 9, 10, 16, 17, 18, 26, 45 };
        for (int slotGlass : SlotGreyGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)7));*/

        player.openInventory(inv);
    }

    public void click(Player player, String itemname) {

        for(TeamTG team : TeamTG.values()) {
            if(itemname.contains(team.getName())) {
                this.menu.main.team.addPlayer(player, team);
                player.closeInventory();
                return;
            }
        }

        if(itemname.equals("§eNe choisir aucune équipe")) {
            this.menu.main.team.addPlayer(player, TeamTG.NULL);
            player.closeInventory();
            return;
        }
    }
}

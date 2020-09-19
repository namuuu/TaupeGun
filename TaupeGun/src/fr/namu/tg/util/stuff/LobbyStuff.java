package fr.namu.tg.util.stuff;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyStuff {

    MainTG main;

    public LobbyStuff(MainTG main) {
        this.main = main;
    }

    public void give(Player player) {
        Inventory inv = player.getInventory();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.ADVENTURE);

        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        inv.setItem(4, ItemBuilder.teamBannerHotbar(ptg.getTeam()));

        if(this.main.info.getHost() != null && this.main.info.getHost().equals(player)) {
            inv.setItem(7, new ItemBuilder(Material.NETHER_STAR, 1).setName("§eMenu du Host").toItemStack());
        }
    }
}

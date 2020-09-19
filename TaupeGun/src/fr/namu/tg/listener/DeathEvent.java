package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

    private MainTG main;

    public DeathEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        event.setDeathMessage(null);

        World world = this.main.info.getGameWorld();

        for(ItemStack item : player.getInventory().getContents()) {
            if(item != null && item.getType() != Material.AIR) {
                world.dropItemNaturally(player.getLocation(), item);
            }
        }
        for(ItemStack item : player.getInventory().getArmorContents()) {
            if(item != null && item.getType() != Material.AIR) {
                world.dropItemNaturally(player.getLocation(), item);
            }
        }

        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.SPECTATOR);
        ptg.getTeam().removePlayer(player);
        this.main.info.getGameWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);

        if(ptg.getTaupe() != null) {
            if(ptg.getTaupe().getPlayers().contains(player))  {
                ptg.getTaupe().removePlayer(player);
            }
        }

        if(player.getKiller() == null) {
            Bukkit.broadcastMessage("§7[" + ptg.getTeam().getName() + "§7] §6" + player.getName() + "§e est mort.");
        } else {
            Player killer = player.getKiller();
            PlayerTG ktg = this.main.playertg.get(killer.getUniqueId());

            ktg.addKill();
            Bukkit.broadcastMessage("§7[" + ptg.getTeam().getName() + "§7] §6" + player.getName() + "§e s'est fait tuer par " + "§7[" + ktg.getTeam().getName() + "§7] §6" + killer.getName());
        }

        this.main.win.verifyWin();
    }
}

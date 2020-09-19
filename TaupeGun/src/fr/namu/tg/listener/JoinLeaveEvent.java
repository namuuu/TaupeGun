package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.scoreboard.FastBoard;
import fr.namu.tg.scoreboard.TabList;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinLeaveEvent implements Listener {

    private MainTG main;

    public JoinLeaveEvent(MainTG main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("§a+ §7» §e"+ event.getPlayer().getName());

        if(main.info.getState().equals(StateTG.LOBBY)) {
            joinPlayer(player);
            return;
        }


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage("§c- §7» §e"+ event.getPlayer().getName());

        this.main.info.setPlayerSize(this.main.info.getPlayerSize() - 1);

        this.main.team.addPlayer(player, TeamTG.NULL);
        this.main.boards.remove(player.getUniqueId());
        this.main.playertg.remove(player.getUniqueId());
        this.main.score.updateBoard();
    }








    public void joinPlayer(Player player) {
        player.teleport(main.info.getSpawnLoc());

        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (PotionEffect po : player.getActivePotionEffects())
            player.removePotionEffect(po.getType());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
        this.main.score.updateBoard();

        TabList.setTabList(player,
                new ChatComponentText(
                        " \n" +
                        "§7• §e§lSELENIUM §7•\n" +
                                " \n" +
                                "§7Mini-jeu actuel: §cTaupe-Gun\n" +
                                " "),

                new ChatComponentText(
                        " \n" +
                                " \n" +
                                "§7Faites-vous un plaisir dans la boutique: §e/boutique\n" +
                                "§7Ce plugin a été développé par §cNxmu§7.\n" +
                                " "
                )
        );

        this.main.playertg.put(player.getUniqueId(), new PlayerTG());
        this.main.boards.put(player.getUniqueId(), new FastBoard(player));

        this.main.info.setPlayerSize(this.main.info.getPlayerSize() + 1);

        this.main.team.addPlayer(player, TeamTG.NULL);
        this.main.lobbystuff.give(player);
        this.main.score.updateBoard();
    }
}

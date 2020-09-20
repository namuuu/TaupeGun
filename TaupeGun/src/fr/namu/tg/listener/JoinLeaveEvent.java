package fr.namu.tg.listener;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.enums.TimerTG;
import fr.namu.tg.scoreboard.FastBoard;
import fr.namu.tg.scoreboard.TabList;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinLeaveEvent implements Listener {

    private MainTG main;

    public JoinLeaveEvent(MainTG main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage("§a+ §7» §e"+ event.getPlayer().getName());

        joinPlayer(player);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        event.setQuitMessage("§c- §7» §e"+ event.getPlayer().getName());

        this.main.info.setPlayerSize(this.main.info.getPlayerSize() - 1);
        this.main.boards.remove(player.getUniqueId());
        this.main.score.updateBoard();

        if(this.main.info.getState().equals(StateTG.LOBBY) || this.main.info.getState().equals(StateTG.END)) {
            this.main.team.addPlayer(player, TeamTG.NULL);
            this.main.playertg.remove(player.getUniqueId());
        } else {
            if(ptg.getTeam() == TeamTG.NULL)
                return;

            ptg.getTeam().removePlayer(player);
            if(ptg.getTaupe() != null && ptg.getTaupe().getPlayers().contains(player))
                ptg.getTaupe().removePlayer(player);

            if(this.main.timer.getTimer() < TimerTG.BORDER_SHRINK.getValue()) {
                Bukkit.broadcastMessage(InfoTG.prefix + "§e" +  player.getName() + " §7s'étant déconnecté, ce joueur pourra se reconnecter sans problèmes jusqu'à l'avancement de la bordure.");
                this.main.win.verifyWin();
                return;
            }

            Bukkit.broadcastMessage("§7[" + ptg.getTeam().getName() + "§7] §6" + player.getName() + "§e est mort.");
            World world = this.main.info.getGameWorld();
            this.main.team.addPlayer(player, TeamTG.NULL);
            this.main.playertg.remove(player.getUniqueId());
            this.main.win.verifyWin();

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
        }





    }








    public void joinPlayer(Player player) {

        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);

        if(!this.main.playertg.containsKey(player.getUniqueId()))
            this.main.playertg.put(player.getUniqueId(), new PlayerTG());

        this.main.boards.put(player.getUniqueId(), new FastBoard(player));

        if(this.main.info.getState().equals(StateTG.LOBBY)) {
            player.teleport(main.info.getSpawnLoc());
            Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                @Override
                public void run(){
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }, 3L);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            for (PotionEffect po : player.getActivePotionEffects())
                player.removePotionEffect(po.getType());

            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
            this.main.team.addPlayer(player, TeamTG.NULL);
            this.main.lobbystuff.give(player);
        }



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


        this.main.info.setPlayerSize(this.main.info.getPlayerSize() + 1);

        if(this.main.info.getState().equals(StateTG.TAUPE) || this.main.info.getState().equals(StateTG.PRETAUPE)) {
            PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

            if(this.main.timer.getTimer() < TimerTG.BORDER_SHRINK.getValue()) {
                if(ptg.getTeam() == TeamTG.NULL) {
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(null);
                    this.main.win.verifyWin();
                    Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                        @Override
                        public void run(){
                            player.setGameMode(GameMode.SPECTATOR);
                        }
                    }, 3L);
                    return;
                }

                ptg.getTeam().addPlayer(player);
                if(ptg.getTaupe() != null)
                    ptg.getTaupe().addPlayer(player);
                this.main.win.verifyWin();
                return;
            }

            this.main.team.addPlayer(player, TeamTG.NULL);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            this.main.win.verifyWin();

            Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                @Override
                public void run(){
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }, 3L);
            return;

        }

        this.main.score.updateBoard();
    }
}

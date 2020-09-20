package fr.namu.tg.util;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.enums.*;
import fr.namu.tg.runnable.GameRun;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartUtil {

    private MainTG main;

    public StartUtil(MainTG main) {
        this.main = main;
    }

    public void start() {

        if(!verifySettings()) {
            return;
        }

        this.main.teamfill.autoTeam();
        this.main.teamfill.autoMole();

        setBorder();
        this.main.info.setState(StateTG.TP);
        teleportPlayers();

        this.main.info.setState(StateTG.PRETAUPE);

        GameRun startGame = new GameRun(this.main);
        startGame.runTaskTimer(this.main, 0L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(InfoTG.prefix + "§eVotre période d'invicibilité est maintenant terminée !");
            }
        }.runTaskLater(this.main, 30*20);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()) {
                    player.setHealth(player.getMaxHealth());
                    player.setFoodLevel(20);
                    player.setSaturation(20);
                    Bukkit.broadcastMessage(InfoTG.prefix + "§aVous venez de recevoir le Final Heal !");
                }
            }
        }.runTaskLater(this.main, TimerTG.FINAL_HEAL.getValue()*20);
    }

    private Boolean verifySettings() {
        System.out.println("Vérification de la Configuration...");

        // VERIFIER QU'IL N'Y A PAS PLUS DE TAUPES QUE DE JOUEURS !
        this.main.team.editMoleTeamSize(MoleConfigTG.MOLE_PER_TEAM.getValue());
        this.main.team.editMoleTeamNumber(0);

        int molenb = 0;
        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && team.getTaupe()) {
                molenb += team.getSize();
            }
        }
        System.out.println("Nombre de Taupes... " + molenb + " taupes trouvées !");
        System.out.println("Nombre de Joueurs... " + this.main.info.getPlayerSize() + " joueurs trouvés !");
        if(molenb > this.main.info.getPlayerSize()) {
            this.main.info.getHost().sendMessage(InfoTG.prefix + "§eIl y a trop de taupes pour le nombre de joueurs !");
            System.out.println("Il y a trop de taupes pour le nombre de joueurs !");
            return false;
        }

        //VERIFIER ASSEZ DE PLACE DANS LES EQUIPES
        int sizee = 0;
        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe())
                sizee += team.getSize();
        }
        if(this.main.info.getPlayerSize() > sizee) {
            this.main.info.getHost().sendMessage(InfoTG.prefix + "§eTout les joueurs ne peuvent pas être placés dans des équipes !");
            System.out.println("§eTout les joueurs ne peuvent pas être placés dans des équipes !");
            return false;
        }

        ////////////////////////////////////////////////////
        System.out.println("Vérification complétée ! Tout est bon !");
        return true;
    }

    private void teleportPlayers() {
        Integer ind = 0;
        for (TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe()) {
                World world = this.main.info.getGameWorld();
                WorldBorder wb = world.getWorldBorder();
                double a = ind * 2.0D * Math.PI / Bukkit.getOnlinePlayers().size();
                int x = (int) Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
                int z = (int) Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
                Location loc = new Location(world, x, (world.getHighestBlockYAt(x, z) + 100), z);
                for (Integer i = 0; i < team.getPlayers().size(); i++) {
                    Player player = team.getPlayers().get(i);
                    this.main.gamestuff.set(player);
                    player.teleport(loc);
                }
                ind++;
            }
        }
    }

    private void setBorder() {
        World world = this.main.info.getGameWorld();
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
        world.setPVP(false);
        world.setWeatherDuration(0);
        world.setThunderDuration(0);
        world.setTime(1000L);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRuleValue("doDayLightCycle", "false");
        world.setTime(0L);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(world.getSpawnLocation().getX(), world.getSpawnLocation().getZ());
        worldBorder.setSize(BorderTG.BIG_BORDER.getValue());
        worldBorder.setWarningDistance((int)(worldBorder.getSize() / 7.0D));
    }
}

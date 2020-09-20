package fr.namu.tg.util;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class WinUtil {

    private MainTG main;

    public WinUtil(MainTG main) {
        this.main = main;
    }

    public void verifyWin() {
        List<TeamTG> remaining = new ArrayList<>();

        for (TeamTG team : TeamTG.values()) {
            if(team != TeamTG.NULL && team.getPlayers().size() != 0) {
                remaining.add(team);
            }
        }

        if(remaining.size() == 1) {
            win(remaining.get(0));
        }
    }

    private void win(TeamTG team) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(this.main.info.getSpawnLoc());
        }
        ItemBuilder.spawnFireWork(this.main.info.getSpawnLoc());

        this.main.info.setState(StateTG.END);
        displayKills();
        this.main.score.updateBoard();
        if(team.getTaupe()) {
            Bukkit.broadcastMessage(InfoTG.prefix + "§eL'Équipe " + team.getName() + " §eremportent cette partie de §cTaupe Gun §Eavec un succès foudroyannt !" +
                    " wFélicitations à eux !");
        } else {
            Bukkit.broadcastMessage(InfoTG.prefix + "§eL' " + team.getName() + " §eremportent cette partie de §cTaupe Gun §Eavec un succès foudroyannt !" +
                    " Félicitations à eux !");
        }


        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(this.main, 20*10);
    }

    private void displayKills() {
        List<UUID> uuids = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            uuids.add(player.getUniqueId());
        }
        for(UUID uuid : this.main.playertg.keySet()) {
            if(!uuids.contains(uuid))
                this.main.playertg.remove(uuid);
        }

        Bukkit.broadcastMessage(" ");
        for (Map.Entry<String, Integer> entry : getTop().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            PlayerTG psd = this.main.playertg.get(player.getUniqueId());
            Bukkit.broadcastMessage(psd.getTeam().getName() + "§7 | §c" + player.getName() + "§7 » §6" + psd.getKills() + "§e kills");
        }
        Bukkit.broadcastMessage(" ");
    }

    private Map<String, Integer> getTop() {
        Map<String, Integer> stats = new HashMap<>();
        this.main.playertg.keySet().forEach(UUID -> stats.put(Bukkit.getPlayer(UUID).getName(), this.main.playertg.get(UUID).getKills()));

        return stats.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}

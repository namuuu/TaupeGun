package fr.namu.tg.util.team;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.TeamTG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamFill {

    private MainTG main;

    public TeamFill(MainTG main) {
        this.main = main;
    }


    public void autoTeam() {
        Collections.shuffle(TeamTG.NULL.getPlayers());
        List<Player> nullMembers = TeamTG.NULL.getPlayers();
        System.out.println("[SkyDefender] Remplissage des Equipes...");

        while(!nullMembers.isEmpty()) {
            if (nullMembers.isEmpty())
                break;

            Player player = nullMembers.get(0);
            PlayerTG ptg = this.main.playertg.get(player.getUniqueId());
            int ind = 0;

            while(ptg.getTeam() == TeamTG.NULL) {
                for(TeamTG team : TeamTG.values()) {
                    if(ptg.getTeam() == TeamTG.NULL && team.getPlayers().size() == ind && team.getSize() > ind && team.getTaupe() != null && !team.getTaupe()) {
                        this.main.team.addPlayer(player, team);
                        if(ptg.getTeam() != TeamTG.NULL && TeamTG.NULL.getPlayers().contains(player)) {
                            TeamTG.NULL.getPlayers().remove(player);
                        }
                        System.out.println(player.getName() + " a été attribué à l'" + team.getName());
                    }
                }

                if(ind == 30) {
                    return;
                }
                ind++;
            }

            System.out.println("Joueurs restants à attribuer:" + nullMembers.size());
        }
    }

    public void autoMole() {
        List<Player> players = new ArrayList<>();
        Integer indi = 0;

        if(ScenarioTG.APOCALYPSE.getValue()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerTG ptg = this.main.playertg.get(player.getUniqueId());
                if(ptg.getTaupe() == null) {
                    players.add(player);
                }
            }
        } else {
            while(indi < 30) {
                for(TeamTG team : TeamTG.values()) {
                    if(indi < team.getPlayers().size()) {
                        players.add(team.getPlayers().get(indi));
                    }
                }
                indi++;
            }
        }






        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && team.getTaupe()) {
                for(Integer ind = 0; ind < team.getSize(); ind++) {
                    Player player = players.get(0);
                    PlayerTG ptg = this.main.playertg.get(player.getUniqueId());
                    this.main.team.addPlayerToMoleTeam(player, team);
                    players.remove(0);
                    ptg.setRevealed(false);
                    this.main.kitStuff.setKit(player);
                    System.out.println(player.getName() + " a été attribué à l'" + team.getName());

                    if(players.isEmpty())
                        return;
                }
            }
        }
    }
}

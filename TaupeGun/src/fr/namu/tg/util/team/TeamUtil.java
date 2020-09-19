package fr.namu.tg.util.team;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.MoleConfigTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamUtil {

    MainTG main;

    public TeamUtil(MainTG main) {
        this.main = main;
    }

    public void addPlayer(Player player, TeamTG team) {
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        if(team.getSize() <= team.getPlayers().size()) {
            player.sendMessage(ChatColor.RED + "Il y a trop de joueurs dans cette équipe !");
            return;
        }

        if(ptg.getTeam() != null) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam(ptg.getTeam().getName()).removeEntry(player.getName());
            ptg.getTeam().removePlayer(player);
        }

        ptg.setTaupe(null);
        ptg.setTeam(team);
        team.addPlayer(player);

        player.setPlayerListName("§7[" + team.getName() + "§7] " + player.getName());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team.getName()).addEntry(player.getName());

        player.getInventory().setItem(4, ItemBuilder.teamBannerHotbar(team));

        if(!main.info.getState().equals(StateTG.LOBBY))
            return;

        if(team.equals(TeamTG.NULL))
            return;

        player.sendMessage(ChatColor.GRAY + "Vous avez rejoint l'" + team.getName());
    }

    public void addPlayerToMoleTeam(Player player, TeamTG team) {
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        ptg.setTaupe(team);
        team.addPlayer(player);
    }

    public void editMoleTeamNumber(int value) {
        MoleConfigTG.TEAM_OF_MOLES.setValue(MoleConfigTG.TEAM_OF_MOLES.getValue() + value);
        int teamNB = MoleConfigTG.TEAM_OF_MOLES.getValue();

        List<TeamTG> moleteams = new ArrayList<>();
        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && team.getTaupe()) {
                moleteams.add(team);
            }
        } // MOLETEAMS EST A PRESENT LA LISTE COMPLETE DES EQUIPES DE TAUPES.

        for(TeamTG team : moleteams) {
            if(teamNB != 0) {
                team.setSize(MoleConfigTG.MOLE_PER_TEAM.getValue());
                teamNB--;
            } else {
                team.setSize(0);
            }
        } // CHAQUE ÉQUIPE VALIDE A ETE MISE A LA BONNE TAILLE



    }
    public void editMoleTeamSize(int value) {
        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && team.getTaupe()) {
                team.setSize(value);
            }
        }
    }
}

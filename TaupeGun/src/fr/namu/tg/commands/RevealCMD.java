package fr.namu.tg.commands;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RevealCMD implements CommandExecutor {

    private MainTG main;

    public RevealCMD(MainTG main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player)sender;
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateTG.TAUPE)) {
            player.sendMessage(InfoTG.prefix + "§eVous ne pouvez pas utiliser cela maintenant !");
            return true;
        }
        if(ptg.getTaupe() == null) {
            player.sendMessage(InfoTG.prefix + "§eVous n'êtes pas une taupe !");
            return true;
        }
        if(ptg.getRevealed() == true) {
            player.sendMessage(InfoTG.prefix + "§eVous vous êtes déjà reveal !");
            return true;
        }

        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(ptg.getTeam().getName()).removeEntry(player.getName());
        ptg.getTeam().removePlayer(player);

        ptg.setTeam(ptg.getTaupe());
        ptg.setRevealed(true);

        player.setPlayerListName("§7[" + ptg.getTeam().getName() + "§7] " + player.getName());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(ptg.getTeam().getName()).addEntry(player.getName());

        Bukkit.broadcastMessage(ptg.getTeam().getPrefix() + player.getName() + " §etrahit son équipe et se révèle en étant §ctaupe !");
        this.main.win.verifyWin();

        return true;
    }
}

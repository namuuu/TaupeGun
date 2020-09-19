package fr.namu.tg.commands;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.ScenarioTG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScenarioCMD implements CommandExecutor {

    private MainTG main;

    public ScenarioCMD(MainTG main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player)sender;

        if(args.length == 0) {

            return true;
        }

        if(args[0].contains("list")) {
            String answer = "";
            for(ScenarioTG scenario : ScenarioTG.values()) {
                if(scenario.getValue() != null && scenario.getValue()) {
                    answer += scenario.getName() + "§7, §e";
                }
                if(scenario.getValue() == null && scenario.getNumber() != 0) {
                    answer += scenario.getName() + "§7 (" + scenario.getNumber() + "), §e";
                }
            }
            player.sendMessage("§cListe des Scénarios Actifs: §e");
            player.sendMessage(answer);
            return true;
        }

        return true;
    }
}

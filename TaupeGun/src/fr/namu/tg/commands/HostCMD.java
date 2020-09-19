package fr.namu.tg.commands;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostCMD implements TabExecutor {

    private MainTG main;

    public HostCMD(MainTG main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;

        Player player = (Player)sender;

        if(!player.isOp() && !this.main.info.getHost().equals(player) && !player.hasPermission("host.use")) {
            player.sendMessage(InfoTG.prefix + "§eVous n'avez pas les permissions nécessaires pour réaliser cette commande !");
            return true;
        }
        if(args.length == 0) {
            player.sendMessage("§eMenu d'aide : En développement");
            return true;
        }

        switch (args[0]) {
            case "takehost":
                if(this.main.info.getHost() != null) {
                    player.sendMessage(InfoTG.prefix + "§eL'Host n'est pas disponible !");
                    break;
                }
                this.main.info.setHost(player);
                this.main.lobbystuff.give((player));
                this.main.score.updateBoard();
                break;
            case "leavehost":
                if(this.main.info.getHost() == null || !this.main.info.getHost().equals(player)) {
                    player.sendMessage(InfoTG.prefix + "§eVous n'êtes pas le Host !");
                    break;
                }
                System.out.println("waou");
                this.main.info.setHost(null);
                this.main.lobbystuff.give(player);
                this.main.score.updateBoard();
                break;
            case "startStuff":
                this.main.gamestuff.save(player);
                this.main.lobbystuff.give(player);
                player.teleport(this.main.info.getSpawnLoc());
                break;
            case "heal":
                Bukkit.broadcastMessage(InfoTG.prefix + "§aLe Host a donné un final heal !");
                for(Player players : Bukkit.getOnlinePlayers()) {
                    players.setHealth(player.getMaxHealth());
                    players.setFoodLevel(20);
                    players.setSaturation(20);
                }
        }
        return true;
    }




    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        String[] tabe = {"takehost", "leavehost", "heal"};
        List<String> tab = new ArrayList<>(Arrays.asList(tabe));
        if (args.length == 0)
            return tab;
        if (args.length == 1) {
            for (int i = 0; i < tab.size(); i++) {
                for (int j = 0; j < tab.get(i).length() && j < args[0].length(); j++) {
                    if (tab.get(i).charAt(j) != args[0].charAt(j)) {
                        tab.remove(i);
                        i--;
                        break;
                    }
                }
            }
            return tab;
        }
        return null;
    }
}

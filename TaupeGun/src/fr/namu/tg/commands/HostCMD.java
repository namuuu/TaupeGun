package fr.namu.tg.commands;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.File;
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
            case "saveconfig":
                if(args.length != 2) {
                    player.sendMessage(InfoTG.prefix + "§eVeuillez préciser le nom du fichier !");
                    return true;
                }
                this.main.config.saveConfig(args[1]);
                player.sendMessage(InfoTG.prefix + "§aLa configuration §e" + args[1] + " §aa été enregistrée avec succès !");
                return true;
            case "loadconfig":
                if(args.length != 2) {
                    player.sendMessage(InfoTG.prefix + "§eVeuillez préciser le nom du fichier !");
                    return true;
                }
                if(!this.main.info.getState().equals(StateTG.LOBBY)) {
                    player.sendMessage(InfoTG.prefix + "§eLa partie a déjà commencé, vous ne pouvez plus réaliser cette action !");
                }
                this.main.config.loadConfig(args[1]);
                player.sendMessage(InfoTG.prefix + "§aLa configuration §e" + args[1] + " §aa été modifiée avec succès !");
                return true;
            case "removeconfig":
                if(args.length != 2) {
                    player.sendMessage(InfoTG.prefix + "§eVeuillez préciser le nom du fichier !");
                    return true;
                }
                File filetodelete = new File(this.main.getDataFolder() + "/config/" + args[1] + ".yml");
                if(!filetodelete.exists()) {
                    player.sendMessage(InfoTG.prefix + "§eCette configuration n'existe pas !");
                    return true;
                }
                filetodelete.delete();
                player.sendMessage(InfoTG.prefix + "§aLa configuration §e" + args[1] + " §aa été supprimée avec succès !");
                return true;
            case "listconfigs":
                File configfolder = new File (this.main.getDataFolder() + "/config");
                if(configfolder.exists()) {
                    player.sendMessage("§cListe des configurations disponibles:");
                    String filelist = "§e";
                    for(String filename : configfolder.list()) {
                        filelist += filename + "§7, §e";
                    }
                    player.sendMessage(filelist);
                }
                return true;
        }
        return true;
    }




    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        String[] tabe = {"takehost", "leavehost", "heal", "saveconfig", "loadconfig"};
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

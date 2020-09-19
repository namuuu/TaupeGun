package fr.namu.tg.commands;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TaupeCMD implements CommandExecutor {

    private MainTG main;

    public TaupeCMD(MainTG main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player)sender;
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        if(ptg.getTaupe() == null || !this.main.info.getState().equals(StateTG.TAUPE)) {
            player.sendMessage(InfoTG.prefix + "§eVous n'êtes pas une Taupe, vous n'avez donc pas besoin de ces informations pour le moment !");
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(" ");
            player.sendMessage("§cListe des Commandes de Taupes:");
            player.sendMessage("/reveal §7Vous reveal en tant que taupe. (Attention, ceci est définitif)");
            player.sendMessage("/t help §7Obtenir ce message");
            player.sendMessage("/t <message> §7Envoie un message à votre équipe de taupes");
            player.sendMessage("/t list §7Obtenir la liste des Taupes de votre équipe");
            player.sendMessage("/t kit §7Obtenir votre kit de taupe !");
            player.sendMessage(" ");
            return true;
        }

        switch (args[0]) {
            case "help":
                player.sendMessage(" ");
                player.sendMessage("§cListe des Commandes de Taupes:");
                player.sendMessage("/reveal §7Vous reveal en tant que taupe. (Attention, ceci est définitif)");
                player.sendMessage("/t help §7Obtenir ce message");
                player.sendMessage("/t <message> §7Envoie un message à votre équipe de taupes");
                player.sendMessage("/t list §7Obtenir la liste des Taupes de votre équipe");
                player.sendMessage("/t kit §7Obtenir votre kit de taupe !");
                player.sendMessage(" ");
                return true;
            case "list":
                String list = "§e";
                for(Player mole : ptg.getTaupe().getPlayers()) {
                    list += mole.getName() + "§7, §e";
                }
                player.sendMessage("§cListe de votre Équipe de Taupe:");
                player.sendMessage(list);
                return true;
            case "kit":
                this.main.kitStuff.giveKit(player);
                return true;
        }

        String broadcast = "";
        for (int i = 0; i < args.length; i++) {
            broadcast = broadcast + args[i] + " ";
        }
        for(Player mole : ptg.getTaupe().getPlayers()) {
            mole.sendMessage(ptg.getTaupe().getPrefix() + "§f" + player.getName() + ": §c" + broadcast);
        }

        return true;
    }
}

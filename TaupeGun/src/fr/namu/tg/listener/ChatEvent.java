package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private MainTG main;

    public ChatEvent(MainTG main) {
        this.main = main;
    }

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playername = player.getName();
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        if (this.main.info.getState().equals(StateTG.LOBBY)) {
            event.setFormat("§e" + playername + " §7» §f" + event.getMessage());
            return;
        }

        if(ptg.getTeam() != null) {
            event.setFormat("§7[" + ptg.getTeam().getName() + "§7] §e" + playername + " §7» §f" + event.getMessage());
        }


        //Bukkit.broadcastMessage("§7[" + phg.getStarDust() + "§7] §e" + playername + " §7» §f" + event.getMessage());
    }
}

package fr.namu.tg.util;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TimerUtil {

    private MainTG main;

    private int timer = 0;

    public TimerUtil(MainTG main) {
        this.main = main;
    }

    public void addTimer() {
        this.timer += 1;
    }
    public int getTimer() {
        return this.timer;
    }


    public String conversion(int timer) {
        String valeur;
        if (timer % 60 > 9) {
            valeur = String.valueOf(timer % 60) + "s";
        } else {
            valeur = "0" + (timer % 60) + "s";
        }
        if (timer / 3600 > 0) {
            if (timer % 3600 / 60 > 9) {
                valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
            } else {
                valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
            }
        } else if (timer / 60 > 0) {
            valeur = String.valueOf(timer / 60) + "m" + valeur;
        }
        return valeur;
    }
    public String getTimerConversed() {
        String valeur;
        if (timer % 60 > 9) {
            valeur = String.valueOf(timer % 60) + "s";
        } else {
            valeur = "0" + (timer % 60) + "s";
        }
        if (timer / 3600 > 0) {
            if (timer % 3600 / 60 > 9) {
                valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
            } else {
                valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
            }
        } else if (timer / 60 > 0) {
            valeur = String.valueOf(timer / 60) + "m" + valeur;
        }
        return valeur;
    }

    public void activatePVP() {
        World world = this.main.info.getGameWorld();
        if(world.getPVP()) {
            return;
        }
        world.setPVP(true);
        Bukkit.broadcastMessage(InfoTG.prefix + "§cLe PVP est désormais activé, un combattant pourrait surgir à tout moment !");
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.WOLF_GROWL, 1.0F, 1.0F);
        }
    }
}

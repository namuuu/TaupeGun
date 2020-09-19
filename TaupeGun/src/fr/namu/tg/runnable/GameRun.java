package fr.namu.tg.runnable;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.enums.BorderTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import fr.namu.tg.enums.TimerTG;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRun extends BukkitRunnable {

    private MainTG main;

    public GameRun(MainTG main) {
        this.main = main;
    }

    public void run() {
        this.main.timer.addTimer();
        this.main.score.updateBoard();

        if(this.main.timer.getTimer() == TimerTG.MOLE_REVEAL.getValue()) {
            this.main.info.setState(StateTG.TAUPE);
            for(TeamTG team : TeamTG.values()) {
                if(team.getTaupe() != null && team.getTaupe()) {
                    for(Player player : team.getPlayers()) {
                        player.sendMessage("§7§m---------------------");
                        player.sendMessage("§fVous êtes une §cTAUPE§f.");
                        player.sendMessage(" ");
                        player.sendMessage("§fVotre but n'est désormais plus de gagner avec vos coéquipiers, mais de la trahir et de gagner avec votre nouvelle équipe," +
                                " de taupes. Pour voir toutes les commandes qui vous sont disponibles, il faut faire §7/t help§f, afin d'à la fois savoir comment communiquer " +
                                "avec vos coéquipiers, savoir qui ils sont, et recevoir votre équipement de taupe. Bonne chance !");
                        player.sendMessage("§7§m---------------------");
                    }
                }
            }
            this.main.score.updateBoard();
            this.main.win.verifyWin();
        }

        if(this.main.timer.getTimer() == TimerTG.PVP.getValue()) {
            this.main.timer.activatePVP();
        }

        if (this.main.timer.getTimer() >= TimerTG.BORDER_SHRINK.getValue()) {
            WorldBorder wb = this.main.info.getGameWorld().getWorldBorder();
            if (wb.getSize() == BorderTG.BIG_BORDER.getValue()) {
                Bukkit.broadcastMessage(InfoTG.prefix + "§eLa §6Bordure §ecommence à se rétrécir, dirigez-vous vers le centre de la Map !");
            }
            if (wb.getSize() > BorderTG.SMALL_BORDER.getValue()) {
                wb.setSize(wb.getSize() - 0.5D);
                wb.setWarningDistance((int)(wb.getSize() / 7.0D));
            }
        }
    }
}

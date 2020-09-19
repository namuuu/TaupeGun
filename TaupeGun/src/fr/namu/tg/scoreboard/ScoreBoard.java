package fr.namu.tg.scoreboard;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TimerTG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoard {

    private MainTG main;

    private String title = "§7• §cTAUPE GUN §7•";

    public ScoreBoard(MainTG main) {
        this.main = main;
    }

    public void updateBoard() {
        StateTG state = this.main.info.getState();

        for(FastBoard board : this.main.boards.values()) {
            if(state.equals(StateTG.LOBBY)) {
                LobbyBoard(board);
            } else if (state.equals(StateTG.PRETAUPE) || state.equals(StateTG.TAUPE)) {
                GameBoard(board);
            }
        }
    }

    private void LobbyBoard(FastBoard board) {
        String[] score = {
                "§7§m----------------------",
                "Joueurs: §c" + this.main.info.getPlayerSize(),
                "§fHost: §c" + "Libre",
                " ",
                "§7play.selenium-pvp.com",
                "§7§m----------------------"
        };

        if(this.main.info.getHost() != null) {
            score[2] = "§fHost: §c" + this.main.info.getHost().getName();
        }


        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private void GameBoard(FastBoard board) {
        Player player = board.getPlayer();
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        String[] score = {
                "§7§m----------------------",
                "Temps: §c" + this.main.timer.getTimerConversed(),
                " ",
                "Vous êtes: §c" + ptg.getTeam().getName(),
                "Taupes: §c",

                " ",
                "§7play.selenium-pvp.com",
                "§7§m----------------------"
        };

        if(this.main.info.getState().equals(StateTG.PRETAUPE)) {
            score[4] = score[4] + this.main.timer.conversion(TimerTG.MOLE_REVEAL.getValue() - this.main.timer.getTimer());
        } else {
            if(ptg.getTaupe() == null) {
                score[4] = score[4] + "✘";
            } else {
                if(!ptg.getRevealed()) {
                    score[4] = score[4] + ptg.getTaupe().getName();
                } else {
                    score[4] = score[4] + ptg.getTaupe().getPlayers().size() + " joueurs";
                }
            }
        }


        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }
}

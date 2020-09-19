package fr.namu.tg.util;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.enums.MoleConfigTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.enums.TeamTG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SetupUtil {

    private MainTG main;
    private InfoTG info;

    public SetupUtil(MainTG main) {
        this.main = main;
        this.info = main.info;
    }


    public void setup() {
        setData();
        setSBTeams();
        resetPlayers();

        this.main.team.editMoleTeamNumber(0);
        this.main.team.editMoleTeamSize(MoleConfigTG.MOLE_PER_TEAM.getValue());

        World world = this.main.info.getGameWorld();
        world.setPVP(false);
        world.setWeatherDuration(0);
        world.setThunderDuration(0);
        world.setTime(1000L);
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("announceAdvancements", "false");
        world.setGameRuleValue("doDayLightCycle", "false");
        world.setGameRuleValue("reducedDebugInfo", "false");
    }

    private void setData() {
        if(!Bukkit.getWorlds().contains(Bukkit.getWorld("world_taupegun"))) {
            this.main.mv.addWorld("world_taupegun", World.Environment.NORMAL, null, WorldType.NORMAL, Boolean.valueOf(true), null);
        } else {
            this.main.mv.deleteWorld("world_taupegun");
            this.main.mv.addWorld("world_taupegun", World.Environment.NORMAL, null, WorldType.NORMAL, Boolean.valueOf(true), null);
        }
        info.setState(StateTG.LOBBY);
        info.setLobby(Bukkit.getWorld("world"));
        info.setGameWorld(Bukkit.getWorld(info.gameWName));
        info.setSpawnLoc(new Location(info.getLobby(), 8.5, 43, 33.5));
    }

    private void setSBTeams() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        for(Team team : sb.getTeams()) {
            team.unregister();
        }

        for(TeamTG team : TeamTG.values()) {
            sb.registerNewTeam(team.getName());
            sb.getTeam(team.getName()).setPrefix(team.getPrefix());
        }
    }

    private void resetPlayers() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            this.main.join.joinPlayer(player);
        }
    }
}

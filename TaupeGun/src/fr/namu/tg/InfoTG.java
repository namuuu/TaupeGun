package fr.namu.tg;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fr.namu.tg.enums.StateTG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InfoTG {

    private StateTG state;

    private World lobby;
    private World gameWorld;

    public String lobbyWName = "world";
    public String gameWName = "world_taupegun";

    private UUID host = null;

    private Location spawnLoc;

    private int playerSize = 0;

    public static String prefix = "§cTG §7» ";

    public InfoTG(MainTG main) {
    }


    public StateTG getState() {
        return state;
    }
    public void setState(StateTG state) {
        this.state = state;
    }



    public Location getSpawnLoc() {
        return spawnLoc;
    }
    public void setSpawnLoc(Location spawnLoc) {
        this.spawnLoc = spawnLoc;
    }

    public int getPlayerSize() {
        return playerSize;
    }
    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }

    public Player getHost() {
        if(host != null) {
            return Bukkit.getPlayer(host);
        }
        return null;
    }
    public void setHost(Player host) {
        if(this.host == null) {
            this.host = host.getUniqueId();
            return;
        }
        if(host == null) {
            this.host = null;
            return;
        }
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public World getLobby() {
        return lobby;
    }

    public void setLobby(World lobby) {
        this.lobby = lobby;
    }
}

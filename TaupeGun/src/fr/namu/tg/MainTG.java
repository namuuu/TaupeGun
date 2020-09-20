package fr.namu.tg;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fr.namu.tg.commands.HostCMD;
import fr.namu.tg.commands.RevealCMD;
import fr.namu.tg.commands.ScenarioCMD;
import fr.namu.tg.commands.TaupeCMD;
import fr.namu.tg.listener.*;
import fr.namu.tg.menu.MenuTG;
import fr.namu.tg.scoreboard.FastBoard;
import fr.namu.tg.scoreboard.ScoreBoard;
import fr.namu.tg.util.*;
import fr.namu.tg.util.stuff.GameStuff;
import fr.namu.tg.util.stuff.KitStuff;
import fr.namu.tg.util.stuff.LobbyStuff;
import fr.namu.tg.util.team.TeamFill;
import fr.namu.tg.util.team.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainTG extends JavaPlugin {

    public final Map<UUID, PlayerTG> playertg = new HashMap<>();
    public final Map<UUID, FastBoard> boards = new HashMap<>();

    public final InfoTG info = new InfoTG(this);
    public final ScoreBoard score = new ScoreBoard(this);

    public final SetupUtil setup = new SetupUtil(this);
    public final ConfigUtil config = new ConfigUtil(this);
    public final TeamUtil team = new TeamUtil(this);
    public final StartUtil start = new StartUtil(this);
    public final TeamFill teamfill = new TeamFill(this);
    public final TimerUtil timer = new TimerUtil(this);
    public final JoinLeaveEvent join = new JoinLeaveEvent(this);
    public final MenuTG menu = new MenuTG(this);
    public final WinUtil win = new WinUtil(this);

    public final LobbyStuff lobbystuff = new LobbyStuff(this);
    public final GameStuff gamestuff = new GameStuff(this);
    public final KitStuff kitStuff = new KitStuff(this);

    public MVWorldManager mv = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();

    @Override
    public void onEnable() {
        setup.setup();

        enableListeners();
        enableCommands();
    }

    @Override
    public void onDisable() {

    }

    private void enableListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinLeaveEvent(this), this);
        pm.registerEvents(new ClickEvent(this), this);
        pm.registerEvents(new InteractEvent(this), this);
        pm.registerEvents(new DamageEvent(this), this);
        pm.registerEvents(new DeathEvent(this), this);
        pm.registerEvents(new ItemSpawnEvent(this), this);
        pm.registerEvents(new PrepareItemEvent(this), this);
        pm.registerEvents(new BreakEvent(this), this);
        pm.registerEvents(new ChatEvent(this), this));
    }

    private void enableCommands() {
        getCommand("host").setExecutor(new HostCMD(this));
        getCommand("reveal").setExecutor(new RevealCMD(this));
        getCommand("scenario").setExecutor(new ScenarioCMD(this));
        getCommand("taupe").setExecutor(new TaupeCMD(this));
    }
}

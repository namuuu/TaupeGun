package fr.namu.tg.util;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConfigUtil {

    private MainTG main;

    public ConfigUtil(MainTG main) {
        this.main = main;
    }

    public void saveConfig(String filename) {
        if(!this.main.getDataFolder().exists())
            this.main.getDataFolder().mkdir();

        File file = new File(this.main.getDataFolder() + "/config", filename + ".yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
                setSections(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = getConfig(file);

        config.set("border.large", BorderTG.BIG_BORDER.getValue());
        config.set("border.small", BorderTG.SMALL_BORDER.getValue());
        config.set("mole.teamsize", MoleConfigTG.MOLE_PER_TEAM.getValue());
        config.set("mole.teamnb", MoleConfigTG.TEAM_OF_MOLES.getValue());
        for(ScenarioTG scenario : ScenarioTG.values()) {
            config.set("scenario." + scenario.getSN() + ".boolean", scenario.getValue());
            config.set("scenario." + scenario.getSN() + ".number", scenario.getNumber());
        }
        config.set("timer.molereveal", TimerTG.MOLE_REVEAL.getValue());
        config.set("timer.pvp", TimerTG.PVP.getValue());
        config.set("timer.finalheal", TimerTG.FINAL_HEAL.getValue());
        config.set("timer.border", TimerTG.BORDER_SHRINK.getValue());

        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe()) {
                config.set("team." + team.getShortname(), team.getSize());
            }
        }

        Integer slot = 0;
        while(slot != 64) {
            config.set("item." + slot, null);
            slot++;
        }
        for (Map.Entry<Integer, ItemStack> entry : this.main.gamestuff.itemStarts.entrySet()) {
            config.set("item." + entry.getKey(), entry.getValue());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(String filename) {
        if(!this.main.getDataFolder().exists())
            return;

        File file = new File(this.main.getDataFolder() + "/config", filename + ".yml");

        if(!file.exists())
            return;

        FileConfiguration config = getConfig(file);

        BorderTG.BIG_BORDER.setValue(config.getInt("border.large"));
        BorderTG.SMALL_BORDER.setValue(config.getInt("border.small"));
        MoleConfigTG.MOLE_PER_TEAM.setValue(config.getInt("mole.teamsize"));
        MoleConfigTG.TEAM_OF_MOLES.setValue(config.getInt("mole.teamnb"));

        for(ScenarioTG scenario : ScenarioTG.values()) {
            scenario.setValue(config.getBoolean("scenario." + scenario.getSN() + ".boolean"));
            scenario.setNumber(config.getInt("scenario." + scenario.getSN() + ".number"));
        }

        TimerTG.MOLE_REVEAL.setValue(config.getInt("timer.molereveal"));
        TimerTG.PVP.setValue(config.getInt("timer.pvp"));
        TimerTG.FINAL_HEAL.setValue(config.getInt("timer.finalheal"));
        TimerTG.BORDER_SHRINK.setValue(config.getInt("timer.border"));

        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe()) {
                team.setSize(config.getInt("team." + team.getShortname()));

                if(team.getSize() < team.getPlayers().size()) {
                    while(team.getSize() != team.getPlayers().size()) {
                        Player get = team.getPlayers().get(0);
                        this.main.team.addPlayer(get, TeamTG.NULL);
                    }
                }
            }
        }

        this.main.gamestuff.itemStarts.clear();
        Integer slot = 0;
        while(slot != 64) {
            if(config.getItemStack("item." + slot) != null) {
                this.main.gamestuff.itemStarts.put(slot, config.getItemStack("item." + slot));
            }
            slot++;
        }


    }







    private FileConfiguration getConfig(File file) {
        return (FileConfiguration) YamlConfiguration.loadConfiguration(file);
    }

    private void setSections(File file) {
        FileConfiguration config = getConfig(file);

        config.createSection("border.large");
        config.createSection("boarder.small");
        config.createSection("mole.teamsize");
        config.createSection("mole.teamnb");
        for(ScenarioTG scenario : ScenarioTG.values()) {
            config.createSection("scenario." + scenario.getSN() + ".boolean");
            config.createSection("scenario." + scenario.getSN() + ".number");
        }
        config.createSection("timer.molereveal");
        config.createSection("timer.pvp");
        config.createSection("timer.finalheal");
        config.createSection("timer.border");

        for(TeamTG team : TeamTG.values()) {
            if(team.getTaupe() != null && !team.getTaupe()) {
                config.createSection("team." + team.getShortname());
            }
        }

        Integer slot = 0;
        while(slot != 64) {
            config.createSection("item." + slot);
            slot++;
        }
    }
}

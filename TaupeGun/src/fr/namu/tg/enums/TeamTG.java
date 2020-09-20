package fr.namu.tg.enums;

import fr.namu.tg.util.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum TeamTG {

    RED("§cÉquipe Rouge", "§7[§cR§7] §c", "rouge", false, 9, DyeColor.RED, new ArrayList<>()),
    ORANGE("§6Équipe Orange","§7[§6O§7] §6", "orange", false, 9, DyeColor.ORANGE, new ArrayList<>()),
    YELLOW("§eÉquipe Jaune","§7[§eJ§7] §e", "jaune", false, 9, DyeColor.YELLOW, new ArrayList<>()),
    GREEN("§aÉquipe Verte","§7[§aV§7] §a", "vert", false,9, DyeColor.GREEN, new ArrayList<>()),
    AQUA("§bÉquipe Cyan","§7[§bC§7] §b", "aqua", false,9, DyeColor.LIGHT_BLUE, new ArrayList<>()),
    BLUE("§9Équipe Bleue", "§7[§9B§7] §9", "bleu", false, 9, DyeColor.BLUE, new ArrayList<>()),
    PINK("§dÉquipe Rose","§7[§dR§7] §d", "rose", false,9, DyeColor.PINK, new ArrayList<>()),
    PURPLE("§5Équipe Mauve","§7[§5M§7] §5", "mauve", false,9,  DyeColor.PURPLE, new ArrayList<>()),

    TAUPE1("§4Taupe 1", "§7[§4T1§7] §c", "t1", true, 9, DyeColor.RED, new ArrayList<>()),
    TAUPE2("§4Taupe 2", "§7[§4T2§7] §c", "t2", true, 9, DyeColor.RED, new ArrayList<>()),
    TAUPE3("§4Taupe 3", "§7[§4T3§7] §c", "t3", true, 9, DyeColor.RED, new ArrayList<>()),
    TAUPE4("§4Taupe 4", "§7[§4T4§7] §c", "t4", true, 9, DyeColor.RED, new ArrayList<>()),

    NULL("§fAucune Équipe","§7", "null", null, 99, DyeColor.WHITE, new ArrayList<>());
    ;

    private final String teamName;
    private final String teamPrefix;
    private final String shortname;
    private final Boolean isTaupe;
    private int size;
    private final DyeColor color;
    private List<Player> list;

    TeamTG(String teamName, String teamPrefix, String shortname, Boolean isTaupe, int size, DyeColor color, List<Player> list) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
        this.shortname = shortname;
        this.isTaupe = isTaupe;
        this.size = size;
        this.color = color;
        this.list = list;
    }

    public String getName() {
        return this.teamName;
    }
    public String getPrefix() { return this.teamPrefix; }

    public void addSize() {this.size++;}
    public void decSize() {this.size--;}
    public void setSize(int value) {this.size = value;}
    public int getSize() {
        return this.size;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void addPlayer(Player player) {
        this.list.add(player);
    }

    public void removePlayer(Player player) {
        this.list.remove(player);
    }

    public List<Player> getPlayers() {
        return this.list;
    }

    public Boolean getTaupe() {
        return isTaupe;
    }

    public ItemStack itemDisplay() {
        return ItemBuilder.bannerColored(teamName + "§7: §e" + size, color);
    }

    public void click(ClickType click) {
        if(click == ClickType.LEFT) {
            if(size != 0)
                size--;
        } else if (click == ClickType.RIGHT) {
            size++;
        }
    }

    public String getShortname() {
        return shortname;
    }
}

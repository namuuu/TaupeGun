package fr.namu.tg.enums;

import fr.namu.tg.MainTG;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum ScenarioTG {
    APOCALYPSE("TG Apocalypse", "apo", Material.BOOK_AND_QUILL, Boolean.valueOf(false), 0, new String[] {}),
    CAT_EYES("§eCat Eyes", "cateyes", Material.EYE_OF_ENDER, Boolean.valueOf(true), 0, new String[] {}),
    CUT_CLEAN("§eCut Clean", "cutclean", Material.GOLD_PICKAXE, Boolean.valueOf(true), 0, new String[] {}),
    HASTEY_BOYS("§eHastey Boys", "hasteyboys", Material.DIAMOND_PICKAXE, Boolean.valueOf(true), 0, new String[] {}),
    DIAMONDLIMIT("§eDiamondLimit", "diamondlimit", Material.DIAMOND, null, 0, new String[] {}),
    MASTERLEVEL("§eMaster Level", "masterlevel", Material.EXP_BOTTLE, null, 1, new String[] {}),
    FASTSMELTING("§eFast Smelting", "fastsmelting", Material.FURNACE, Boolean.valueOf(false), 0, new String[] {}),
    RODLESS("§eRodLess", "rodless", Material.FISHING_ROD, Boolean.valueOf(true), 0, new String[] {}),
    FIRELESS("§eFireLess", "fireless", Material.BLAZE_POWDER, Boolean.valueOf(false), 0, new String[] {}),


    ;

    private String name;
    private final String shortname;

    private Boolean value;

    private int nb;

    private Material mat;

    private String[] lore;

    ScenarioTG(String name, String shortname, Material mat, Boolean value, int nb, String[] lore) {
        this.name = name;
        this.shortname = shortname;
        this.value = value;
        this.nb = nb;
        this.mat = mat;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getValue() {
        return this.value;
    }
    public void setValue(Boolean value) {
        this.value = value;
    }

    public int getNumber() {return this.nb;}
    public void setNumber(int nb) {this.nb = nb;}

    public void switchValue(ClickType click) {
        if(value == null) {
            if(click == ClickType.LEFT) {
                if(nb == 0) {
                    return;
                }
                nb--;
            } else {
                nb++;
            }
            return;
        }
        if(value) {
            setValue(false);
            return;
        } else if (value == false) {
            setValue(true);
        }
    }

    public ItemStack itemDisplay() {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        if(value == null) {
            im.setLore(Arrays.asList(new String[]{"§bValeur : §e" + this.nb}));
            if(nb == 0) {
                im.setLore(Arrays.asList(new String[]{"§cEst inactif"}));
            }
        } else {
            if(value) {
                im.setLore(Arrays.asList(new String[]{"§aEst actif"}));
            } else {
                im.setLore(Arrays.asList(new String[]{"§cEst inactif"}));
            }
        }
        item.setItemMeta(im);
        return item;
    }

    public String[] getLore() {
        return lore;
    }

    public String getSN() {
        return shortname;
    }
}

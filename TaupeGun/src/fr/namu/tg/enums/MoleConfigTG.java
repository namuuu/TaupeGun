package fr.namu.tg.enums;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

public enum MoleConfigTG {
    TEAM_OF_MOLES("§fNombre d'Équipes de taupes", 1),
    MOLE_PER_TEAM("§fTaupes par Équipes", 1),
    ;

    private final String name;
    private int value;

    MoleConfigTG(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public void click(ClickType click) {
        if(click == ClickType.LEFT) {
            if(value != 0)
                value--;
        } else if (click == ClickType.RIGHT) {
            value++;
        }
    }
}

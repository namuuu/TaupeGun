package fr.namu.tg;

import fr.namu.tg.enums.KitTG;
import fr.namu.tg.enums.TeamTG;

public class PlayerTG {

    private TeamTG team;

    private TeamTG taupe = null;
    private Boolean isRevealed = null;

    private KitTG kit = null;
    private Boolean hasClaimedKit = false;

    private int kills = 0;
    private int diamondleft;

    public TeamTG getTeam() {
        return team;
    }
    public void setTeam(TeamTG team) {
        this.team = team;
    }

    public TeamTG getTaupe() {
        return taupe;
    }
    public void setTaupe(TeamTG taupe) {
        this.taupe = taupe;
    }

    public Boolean getRevealed() {
        return isRevealed;
    }
    public void setRevealed(Boolean revealed) {
        isRevealed = revealed;
    }

    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public void addKill() { this.kills++; }

    public int getDiamondleft() {
        return diamondleft;
    }
    public void setDiamondleft(int diamondleft) {
        this.diamondleft = diamondleft;
    }
    public void hasMinedDiamond() { this.diamondleft--; }

    public Boolean getHasClaimedKit() {
        return hasClaimedKit;
    }
    public void setHasClaimedKit(Boolean hasClaimedKit) {
        this.hasClaimedKit = hasClaimedKit;
    }

    public KitTG getKit() {
        return kit;
    }
    public void setKit(KitTG kit) {
        this.kit = kit;
    }
}

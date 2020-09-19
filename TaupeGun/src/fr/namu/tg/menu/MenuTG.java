package fr.namu.tg.menu;

import fr.namu.tg.MainTG;

public class MenuTG {

    public MainTG main;

    public final TeamSelectMenu teamselect = new TeamSelectMenu(this);
    public final HostMainMenu hostmain = new HostMainMenu(this);
    public final ScenarioMenu scenarioMenu = new ScenarioMenu(this);
    public final TeamEditMenu teamEdit = new TeamEditMenu(this);
    public final TimerMenu timerMenu = new TimerMenu(this);

    public MenuTG(MainTG main) {
        this.main = main;
    }
}

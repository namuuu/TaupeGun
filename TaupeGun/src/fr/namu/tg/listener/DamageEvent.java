package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamageEvent implements Listener {

    private MainTG main;

    public DamageEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        if(this.main.info.getState().equals(StateTG.LOBBY) || this.main.timer.getTimer() <= 30) {
            event.setCancelled(true);
        }

        if(ScenarioTG.FIRELESS.getValue()) {
            if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.LAVA) || event.getCause().equals(DamageCause.FIRE_TICK))
                event.setCancelled(true);
        }
    }
}

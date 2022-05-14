package xyz.blujay.autototem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.blujay.autototem.AutoTotem;
import xyz.blujay.autototem.enums.ResetReason;

public class PlayerDeathEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent e){
        var api = AutoTotem.getInstance().getAPI();
        if(api.resetCooldownOnDeath){
            api.resetCooldown(e.getEntity(), ResetReason.REASON_DEATH, null);
        }
    }
}

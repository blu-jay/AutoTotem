package xyz.blujay.autototem.classes;

import java.time.Instant;

public class PlayerCooldown {
    public boolean bypassCooldown = false;
    public boolean coolDownStarted = false;
    public Instant coolDown;

    public PlayerCooldown(){
        this.coolDown = Instant.EPOCH;
    }

    public PlayerCooldown(Instant coolDown, boolean bypassCooldown) {
        this.coolDown = coolDown;
        this.bypassCooldown = bypassCooldown;
    }
}

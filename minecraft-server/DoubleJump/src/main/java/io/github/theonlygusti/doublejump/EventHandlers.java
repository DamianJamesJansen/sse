package io.github.theonlygusti.doublejump;

import io.github.theonlygusti.doublejump.DoubleJump;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class EventHandlers implements Listener {
  private HashMap<DoubleJumper, Boolean> hasLandedOnGround = new HashMap<DoubleJumper, Boolean>();

  @EventHandler
  public void onFlightAttempt(PlayerToggleFlightEvent event) {
    DoubleJumper doubleJumper = DoubleJump.get(event.getPlayer());

    if (doubleJumper != null) {
      event.setCancelled(true);
      doubleJumper.setVelocity();
      doubleJumper.afterVelocity();
      hasLandedOnGround.put(doubleJumper, false);
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    DoubleJumper doubleJumper = DoubleJump.get(event.getPlayer());

    if (doubleJumper != null) {
      if (event.getPlayer().isOnGround()) {
        if (hasLandedOnGround.get(doubleJumper) == null || !hasLandedOnGround.get(doubleJumper)) {
          doubleJumper.landOnGround();
          hasLandedOnGround.put(doubleJumper, true);
        }
      }
    }
  }
}

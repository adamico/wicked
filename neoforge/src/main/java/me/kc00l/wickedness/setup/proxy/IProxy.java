package me.kc00l.wickedness.setup.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IProxy {
    void init();

    Level getClientWorld();

    Minecraft getMinecraft();

    Player getPlayer();
}

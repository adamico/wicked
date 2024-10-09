package me.kc00l.wicked.events;

import me.kc00l.wicked.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class AddWickednessEvent {
    public static void onAnimalDeath(Entity animal, Entity player) {
        Level level = animal.level();

        //TODO: killing monsters removes wickedness?
        if (!(animal instanceof Animal)) return;
        if (level.isClientSide) return;

        if (player instanceof ServerPlayer) {
            Reference.LOG.info("entity {} was damaged by player", animal);
        } else {
            Reference.LOG.info("entity {} was not damaged by player", animal);
        }
    }
}

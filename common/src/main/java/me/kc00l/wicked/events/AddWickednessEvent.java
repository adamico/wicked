package me.kc00l.wicked.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;

public class AddWickednessEvent {
    public static void onAnimalDeath(Entity entity) {
        int wickednessAmount = 1;
        entity.level().addParticle(
                ParticleTypes.EXPLOSION_EMITTER,
                (double) entity.getX() + 0.5,
                (double) entity.getY() + 1,
                (double) entity.getZ() + 0.5,
                0.0,
                0.0,
                0.0
        );
    }
}

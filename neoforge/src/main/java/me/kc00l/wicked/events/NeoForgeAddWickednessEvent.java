package me.kc00l.wicked.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class NeoForgeAddWickednessEvent {
    @SubscribeEvent
    public static void onAnimalDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();

        if (entity instanceof Animal) {
            AddWickednessEvent.onAnimalDeath(entity);
        }
    }
}

package me.kc00l.wicked.common.event;

import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.config.ServerConfig;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import me.kc00l.wicked.util.ModTags;
import me.kc00l.wicked.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class AddWickednessEvent {
    @SubscribeEvent
    public static void onAnimalDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getDirectEntity();
        Level level = entity.level();

        if (level.isClientSide) return;

        if (sourceEntity instanceof ServerPlayer) {
            Reference.LOG.info("entity {} was damaged by player", entity);
            WickednessCap wickednessCap = CapabilityRegistry.getWickedness((LivingEntity) sourceEntity);
            int wickednessAmount = getWickednessAmount(entity);
            int currentWickedness = wickednessCap.addWickedness(wickednessAmount);
            Reference.LOG.info("killing entity {} changed player {} wickedness to {}", entity, sourceEntity, currentWickedness);
        } else {
            Reference.LOG.info("entity {} was not damaged by player", entity);
        }
    }

    private static int getWickednessAmount(Entity entity) {
        EntityType<?> entityType = entity.getType();
        if (bestowsWickedness(entityType)) {
            if (entityType.is(ModTags.Entities.LOW_WICKEDNESS_ENTITIES)) {
                return ServerConfig.LOW_WICKEDNESS_VALUE.get();
            } else if (entityType.is(ModTags.Entities.MID_WICKEDNESS_ENTITIES)) {
                return ServerConfig.MID_WICKEDNESS_VALUE.get();
            } else if (entityType.is(ModTags.Entities.HIGH_WICKEDNESS_ENTITIES)) {
                return ServerConfig.HIGH_WICKEDNESS_VALUE.get();
            }
        } else {
            return 0;
        }

        return (entity instanceof Animal) ? 10 : 0;
    }

    private static boolean bestowsWickedness(EntityType<?> entityType) {
        return (entityType.is(ModTags.Entities.LOW_WICKEDNESS_ENTITIES) ||
                entityType.is(ModTags.Entities.MID_WICKEDNESS_ENTITIES) ||
                entityType.is(ModTags.Entities.HIGH_WICKEDNESS_ENTITIES)
                );
    }
}

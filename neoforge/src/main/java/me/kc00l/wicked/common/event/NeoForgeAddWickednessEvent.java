package me.kc00l.wicked.common.event;

import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
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

import java.util.Map;

@EventBusSubscriber
public class NeoForgeAddWickednessEvent {
    private static final Map<Object, Integer> ANIMAL_WICKEDNESS_MAP =
            Map.of(
                    EntityType.ALLAY, 80,
                    EntityType.ARMADILLO, 50,
                    EntityType.AXOLOTL, 50,
                    EntityType.BAT, 50,
                    EntityType.CAMEL, 50,
                    EntityType.CAT, 80,
                    EntityType.DOLPHIN, 80,
                    EntityType.FOX, 50,
                    EntityType.HORSE, 50
            );

    @SubscribeEvent
    public static void onAnimalDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getDirectEntity();
        Level level = entity.level();

        if (level.isClientSide) return;

        if (sourceEntity instanceof ServerPlayer) {
            Reference.LOG.info("entity {} was damaged by player", entity);
            WickednessCap wickedness = CapabilityRegistry.getWickedness((LivingEntity) sourceEntity);
            int wickednessAmount = getWickednessAmount(entity);
            int currentWickedness = wickedness.addWickedness(wickednessAmount);
            Reference.LOG.info("killing entity {} changed player {} wickedness to {}", entity, sourceEntity, currentWickedness);
        } else {
            Reference.LOG.info("entity {} was not damaged by player", entity);
        }
    }

    private static int getWickednessAmount(Entity entity) {
        EntityType<?> entityType = entity.getType();

        if (ANIMAL_WICKEDNESS_MAP.containsKey(entityType)) {
            return ANIMAL_WICKEDNESS_MAP.get(entity.getType());
        } else if (entity instanceof Animal) {
            return 10;
        } else {
            return 0;
        }
    }
}

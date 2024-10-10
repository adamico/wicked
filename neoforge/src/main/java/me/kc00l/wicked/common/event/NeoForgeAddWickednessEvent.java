package me.kc00l.wicked.common.event;

import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import me.kc00l.wicked.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class NeoForgeAddWickednessEvent {
    @SubscribeEvent
    public static void onAnimalDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getDirectEntity();
        Level level = entity.level();

        //TODO: killing monsters removes wickedness?
        if (!(entity instanceof Animal)) return;
        if (level.isClientSide) return;

        if (sourceEntity instanceof ServerPlayer) {
            Reference.LOG.info("entity {} was damaged by player", entity);
            WickednessCap wickedness = CapabilityRegistry.getWickedness((LivingEntity) sourceEntity);
            int currentWickedness = wickedness.addWickedness(10);
            Reference.LOG.info("killing entity {} changed player {} wickedness to {}", entity, sourceEntity, currentWickedness);
        } else {
            Reference.LOG.info("entity {} was not damaged by player", entity);
        }
    }
}

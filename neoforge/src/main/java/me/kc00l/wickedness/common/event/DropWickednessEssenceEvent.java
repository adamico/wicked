package me.kc00l.wickedness.common.event;

import me.kc00l.wickedness.common.capability.WickednessCap;
import me.kc00l.wickedness.common.item.ModItems;
import me.kc00l.wickedness.setup.registry.CapabilityRegistry;
import me.kc00l.wickedness.util.Reference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class DropWickednessEssenceEvent {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        Entity sourceEntity = event.getSource().getDirectEntity();

        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;
        if (sourceEntity == null) {
            Reference.LOG.info("player {} wasn't killed by another entity", player);
            return;
        }

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        int currentWickedness = wickednessCap.getCurrentWickedness();

        if (currentWickedness > 0) {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY() + 0.5, player.getZ(),
                    new ItemStack(ModItems.WICKEDNESS_ESSENCE.get(), Math.max(1, currentWickedness/10))
            ));
        }

        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS);

        wickednessCap.setWickedness(0);
    }
}

package me.kc00l.wickedness.common.event;

import com.natamus.collective.functions.BlockPosFunctions;
import com.natamus.collective.functions.EntityFunctions;
import me.kc00l.wickedness.common.capability.WickednessCap;
import me.kc00l.wickedness.setup.config.ServerConfig;
import me.kc00l.wickedness.setup.registry.CapabilityRegistry;
import me.kc00l.wickedness.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class SpawnJackOLanternEvent {
    @SubscribeEvent
    private static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);

        if (!(player instanceof ServerPlayer)) return;
        if (wickednessCap == null) return;
        if (player.getTags().contains(Reference.MOD_ID + ".ispunished")) return;
        if (wickednessCap.getCurrentWickedness() < ServerConfig.INIT_MAX_WICKEDNESS.get()  ) return;

        spawnJackOLantern(level, player);
        player.getTags().add(Reference.MOD_ID + ".ispunished");
    }

    @SubscribeEvent
    private static void onJackDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        Entity sourceEntity = event.getSource().getDirectEntity();

        if (!(entity instanceof Vindicator vindicator)) return;
        if (level.isClientSide) return;

        if (!(sourceEntity instanceof Player player)) {
            Reference.LOG.info("vindicator {} wasn't killed by a player", vindicator);
            return;
        }

        player.getTags().remove(Reference.MOD_ID + ".ispunished");
    }

    private static void spawnJackOLantern(Level level, Player player) {
        BlockPos position = player.blockPosition().immutable();
        ServerLevel serverLevel = (ServerLevel) level;

        Vindicator vindicator = EntityType.VINDICATOR.create(level);
        vindicator.setCustomName(Component.literal("Jack-o'-lantern"));
        vindicator.setPersistenceRequired();
        vindicator.setCanPickUpLoot(false);

        vindicator.getAttribute(Attributes.SCALE).setBaseValue(2d);
        vindicator.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
        vindicator.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(64);
        vindicator.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200);
        vindicator.setHealth(200);
        vindicator.setSpeed(1);

        EntityFunctions.getTargetSelector(vindicator).removeAllGoals(goal -> true);
        EntityFunctions.getTargetSelector(vindicator).addGoal(2,
                new NearestAttackableTargetGoal<>(vindicator, Player.class, false));

        player.sendSystemMessage(Component.translatable("message.wickedness.naughty"));
        BlockPos spawnPos = BlockPosFunctions.getSurfaceBlockPos(serverLevel, position.getX(), position.getZ());

        vindicator.setPos(spawnPos.getX()+Math.random()*30, spawnPos.getY()+1, spawnPos.getZ()+Math.random()*30);

        level.addFreshEntity(vindicator);
    }
}

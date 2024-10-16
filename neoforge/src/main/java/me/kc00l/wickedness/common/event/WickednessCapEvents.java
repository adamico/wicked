package me.kc00l.wickedness.common.event;

import me.kc00l.wickedness.common.capability.WickednessCap;
import me.kc00l.wickedness.setup.registry.CapabilityRegistry;
import me.kc00l.wickedness.setup.config.ServerConfig;
import me.kc00l.wickedness.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class WickednessCapEvents {
    public static double MEAN_TPS = 20.0;

    @SubscribeEvent
    public static void preReduceWickednessOnTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (player.getCommandSenderWorld().getGameTime() % ServerConfig.WICKEDNESS_DECAY_INTERVAL.get() != 0) return;

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        if (wickednessCap == null) return;

        boolean sync = false;

        if (wickednessCap.getCurrentWickedness() > 0 && wickednessCap.wickednessDecays()) {
            Reference.LOG.info("Trying reducing wickedness");
            double decayPerSecond = Math.max(1, ((int) MEAN_TPS / ServerConfig.WICKEDNESS_DECAY_INTERVAL.get()));
            int currentWickedness = wickednessCap.removeWickedness((int) decayPerSecond);
            sync = true;
            Reference.LOG.info("Reduced wickedness by {}, new wickedness is {}", decayPerSecond, currentWickedness);
        }

        int max = ServerConfig.INIT_MAX_WICKEDNESS.get();
        if (wickednessCap.getMaxWickedness() != max) {
            wickednessCap.setMaxWickedness(max);
            sync = true;
        }

        if (sync) wickednessCap.syncToClient(serverPlayer);
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncPlayerEvent(event.getEntity());
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        syncPlayerEvent(event.getEntity());
    }

    @SubscribeEvent
    public static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncPlayerEvent(event.getEntity());
    }

    @SubscribeEvent
    public static void setMeanTpsOnTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (player.level().getGameTime() % 600 == 0 && player.getServer() != null) {
            double meanTickTime = mean(Objects.requireNonNull(player.getServer().getTickTime(player.level().dimension()))) * 1.0E-6D;
            double meanTPS = Math.min(1000.0 / meanTickTime, 20);
            WickednessCapEvents.MEAN_TPS = Math.max(1, meanTPS);
        }
    }

    private static void syncPlayerEvent(Player playerEntity) {
        if (playerEntity instanceof ServerPlayer serverPlayer) {
            WickednessCap wickednessCap = CapabilityRegistry.getWickedness(playerEntity);

            if (wickednessCap == null) return;

            wickednessCap.syncToClient(serverPlayer);

            int currentWickedness = wickednessCap.getCurrentWickedness();
            Reference.LOG.info("Hello {}, your current wickedness is {}", playerEntity, currentWickedness);
        }
    }

    private static long mean(long[] values) {
        long sum = 0L;
        for (long v : values) sum += v;
        return sum / values.length;
    }

}

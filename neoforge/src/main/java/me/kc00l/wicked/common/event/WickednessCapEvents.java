package me.kc00l.wicked.common.event;

import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.util.WickednessUtil;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import me.kc00l.wicked.setup.config.ServerConfig;
import me.kc00l.wicked.util.Reference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class WickednessCapEvents {
    public static double MEAN_TPS = 20.0;

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (player.getCommandSenderWorld().getGameTime() % ServerConfig.WICKEDNESS_DECAY_INTERVAL.get() != 0) return;

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        if (wickednessCap == null) return;

        boolean sync = false;
        boolean forceSync = player.level().getGameTime() % 60 == 0;

        Reference.LOG.info("Trying reducing wickedness");
        if (wickednessCap.getCurrentWickedness() > 0 || forceSync) {
            double decayPerSecond = WickednessUtil.getWickednessDecay(player) / Math.max(1, ((int) MEAN_TPS / ServerConfig.WICKEDNESS_DECAY_INTERVAL.get()));
            int currentWickedness = wickednessCap.removeWickedness((int) decayPerSecond);
            sync = true;
            Reference.LOG.info("Reduced wickedness by {}, new wickedness is {}", decayPerSecond, currentWickedness);
        }

        int max = ServerConfig.INIT_MAX_WICKEDNESS.get();
        if (wickednessCap.getMaxWickedness() != max || forceSync) {
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

    private static void syncPlayerEvent(Player playerEntity) {
        if (playerEntity instanceof ServerPlayer serverPlayer) {
            WickednessCap wickedness = CapabilityRegistry.getWickedness(playerEntity);

            if (wickedness == null) return;

            wickedness.syncToClient(serverPlayer);

            int currentWickedness = wickedness.getCurrentWickedness();
            Reference.LOG.info("Hello {}, your current wickedness is {}", playerEntity, currentWickedness);
        }
    }

    @SubscribeEvent
    public static void onTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (player.level().getGameTime() % 600 == 0 && player.getServer() != null) {
            double meanTickTime = mean(player.getServer().getTickTime(player.level().dimension())) * 1.0E-6D;
            double meanTPS = Math.min(1000.0 / meanTickTime, 20);
            WickednessCapEvents.MEAN_TPS = Math.max(1, meanTPS);
        }
    }

    private static long mean(long[] values) {
        long sum = 0L;
        for (long v : values) sum += v;
        return sum / values.length;
    }
}

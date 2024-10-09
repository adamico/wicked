package me.kc00l.wicked.setup.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static ModConfigSpec SERVER_CONFIG;
    public static ModConfigSpec.IntValue INIT_MAX_WICKEDNESS;
    public static ModConfigSpec.IntValue INIT_WICKEDNESS_DECAY;
    public static ModConfigSpec.IntValue WICKEDNESS_DECAY_INTERVAL;

    static {
        ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
        SERVER_BUILDER.comment("Wickedness").push("wickedness");
        INIT_WICKEDNESS_DECAY = SERVER_BUILDER.comment("Base wickedness decay amount").defineInRange("baseDecay", 5, 0, Integer.MAX_VALUE);
        INIT_MAX_WICKEDNESS = SERVER_BUILDER.comment("Base max wickedness").defineInRange("baseMax", 100, 0, Integer.MAX_VALUE);
        WICKEDNESS_DECAY_INTERVAL = SERVER_BUILDER.comment("How often wickedness decays, in ticks")
                .defineInRange("decayInterval", 5, 1, 20);

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}

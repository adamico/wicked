package me.kc00l.wicked.setup.registry;

import me.kc00l.wicked.capability.WickednessCap;
import me.kc00l.wicked.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilityRegistry {
    public static final EntityCapability<WickednessCap, Void> WICKEDNESS_CAPABILITY =
            EntityCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "wickedness"),
                    WickednessCap.class);

    public static WickednessCap getWickedness(final LivingEntity entity) {
        if (entity == null) return null;

        return entity.getCapability(WICKEDNESS_CAPABILITY);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(WICKEDNESS_CAPABILITY, EntityType.PLAYER, (player, ctx) -> new WickednessCap(player));
    }
}

package me.kc00l.wickedness.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTags {
    public static class Entities {
        public static final TagKey<EntityType<?>> LOW_WICKEDNESS_ENTITIES = createTag("low_wickedness_entities");
        public static final TagKey<EntityType<?>> MID_WICKEDNESS_ENTITIES = createTag("mid_wickedness_entities");
        public static final TagKey<EntityType<?>> HIGH_WICKEDNESS_ENTITIES = createTag("high_wickedness_entities");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, name));
        }
    }
}

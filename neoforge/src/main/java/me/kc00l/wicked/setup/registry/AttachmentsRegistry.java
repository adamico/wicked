package me.kc00l.wicked.setup.registry;

import me.kc00l.wicked.common.capability.WickednessData;
import me.kc00l.wicked.util.Reference;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachmentsRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Reference.MOD_ID);

    public static final Supplier<AttachmentType<WickednessData>> WICKEDNESS_ATTACHMENT =
            ATTACHMENT_TYPES.register("wickedness_cap",
                    () -> AttachmentType.serializable(WickednessData::new)
                            .copyOnDeath()
                            .build());
}

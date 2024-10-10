package me.kc00l.wicked.common.item;

import me.kc00l.wicked.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab>CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

    public static final Supplier<CreativeModeTab> WICKED_ITEMS_TAB = CREATIVE_MODE_TAB.register("wicked_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KARMA_COMPASS.get()))
                    .title(Component.translatable("creativetab.wicked.wicked_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.KARMA_COMPASS);
                        output.accept(ModItems.WICKED_ESSENCE);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

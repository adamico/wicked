package me.kc00l.wickedness.common.item;

import me.kc00l.wickedness.common.item.custom.KarmaCompassItem;
import me.kc00l.wickedness.util.Reference;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);

    public static final DeferredItem<Item> KARMA_COMPASS = ITEMS.register("karma_compass",
            () -> new KarmaCompassItem(new Item.Properties().durability(32)));

    public static final DeferredItem<Item> WICKEDNESS_ESSENCE = ITEMS.register("wickedness_essence",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

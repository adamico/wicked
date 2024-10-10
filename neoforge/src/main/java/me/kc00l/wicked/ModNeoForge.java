package me.kc00l.wicked;


import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import me.kc00l.wicked.common.event.CommandEvents;
import me.kc00l.wicked.common.event.AddWickednessEvent;
import me.kc00l.wicked.common.item.ModCreativeModeTabs;
import me.kc00l.wicked.common.item.ModItems;
import me.kc00l.wicked.common.network.Networking;
import me.kc00l.wicked.setup.config.ServerConfig;
import me.kc00l.wicked.setup.proxy.ClientProxy;
import me.kc00l.wicked.setup.proxy.IProxy;
import me.kc00l.wicked.setup.proxy.ServerProxy;
import me.kc00l.wicked.setup.registry.AttachmentsRegistry;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import me.kc00l.wicked.util.Reference;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
    public static IProxy proxy;
    public ModNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) return;

        RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
        Reference.LOG.info("Hello NeoForge world!");
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);

        ModCreativeModeTabs.register(modEventBus);

//        ModCommon.init();
        ModItems.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(Networking::register);
        modEventBus.addListener(CapabilityRegistry::registerCapabilities);
        AttachmentsRegistry.ATTACHMENT_TYPES.register(modEventBus);

        ModNeoForge.proxy = (FMLEnvironment.dist.isClient()) ? new ClientProxy() : new ServerProxy();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.KARMA_COMPASS);
        }
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        NeoForge.EVENT_BUS.register(AddWickednessEvent.class);
        NeoForge.EVENT_BUS.register(CommandEvents.class);
    }
}
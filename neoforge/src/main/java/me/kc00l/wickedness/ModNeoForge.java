package me.kc00l.wickedness;


import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import me.kc00l.wickedness.common.item.ModCreativeModeTabs;
import me.kc00l.wickedness.common.item.ModItems;
import me.kc00l.wickedness.common.network.Networking;
import me.kc00l.wickedness.setup.config.ServerConfig;
import me.kc00l.wickedness.setup.proxy.ClientProxy;
import me.kc00l.wickedness.setup.proxy.IProxy;
import me.kc00l.wickedness.setup.proxy.ServerProxy;
import me.kc00l.wickedness.setup.registry.AttachmentsRegistry;
import me.kc00l.wickedness.setup.registry.CapabilityRegistry;
import me.kc00l.wickedness.util.Reference;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
    public static IProxy proxy;
    public ModNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) return;

        RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);

        ModCreativeModeTabs.register(modEventBus);

//        ModCommon.init();
        ModItems.register(modEventBus);
        modEventBus.addListener(Networking::register);
        modEventBus.addListener(CapabilityRegistry::registerCapabilities);
        AttachmentsRegistry.ATTACHMENT_TYPES.register(modEventBus);

        ModNeoForge.proxy = (FMLEnvironment.dist.isClient()) ? new ClientProxy() : new ServerProxy();
    }

}
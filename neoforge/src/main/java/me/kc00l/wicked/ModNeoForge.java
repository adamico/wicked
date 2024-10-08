package me.kc00l.wicked;


import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import me.kc00l.wicked.events.NeoForgeAddWickednessEvent;
import me.kc00l.wicked.util.Reference;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
    public ModNeoForge(IEventBus modEventBus) {
        if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
            return;
        }

        Reference.LOG.info("Hello NeoForge world!");
        modEventBus.addListener(this::loadComplete);

        ModCommon.init();

        RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        NeoForge.EVENT_BUS.register(NeoForgeAddWickednessEvent.class);
    }
}
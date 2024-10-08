package me.kc00l.wicked;

import com.natamus.collective.check.ShouldLoadCheck;
import me.kc00l.wicked.util.Reference;
import net.fabricmc.api.ModInitializer;

public class ModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
            return;
        }

        Reference.LOG.info("Hello Fabric world!");
        ModCommon.init();
        ModCommon.registerAssets(null);
        ModCommon.setAssets();
    }
}

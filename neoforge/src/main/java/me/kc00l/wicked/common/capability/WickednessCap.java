package me.kc00l.wicked.common.capability;

import me.kc00l.wicked.common.network.Networking;
import me.kc00l.wicked.common.network.PacketUpdateWickedness;
import me.kc00l.wicked.setup.registry.AttachmentsRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class WickednessCap {
    private WickednessData wickednessData;
    LivingEntity entity;

    public WickednessCap(LivingEntity livingEntity) {
        wickednessData = livingEntity.getData(AttachmentsRegistry.WICKEDNESS_ATTACHMENT);
        entity = livingEntity;
    }

    public int getCurrentWickedness() {
        return wickednessData.getWickedness();
    }

    public int setWickedness(int wickedness) {
        if (wickedness > getMaxWickedness()) {
            this.wickednessData.setWickedness(getMaxWickedness());
        } else if (wickedness < 0) {
            this.wickednessData.setWickedness(0);
        } else {
            this.wickednessData.setWickedness(wickedness);
        }

        entity.setData(AttachmentsRegistry.WICKEDNESS_ATTACHMENT, wickednessData);
        return this.getCurrentWickedness();
    }

    public int getMaxWickedness() {
        return wickednessData.getMaxWickedness();
    }

    public void setMaxWickedness(int maxWickedness) {
        wickednessData.setMaxWickedness(maxWickedness);
        entity.setData(AttachmentsRegistry.WICKEDNESS_ATTACHMENT, wickednessData);
    }

    public int removeWickedness(int wickednessAmount) {
        return this.setWickedness(this.getCurrentWickedness() - wickednessAmount);
    }

    public int addWickedness(int wickednessAmount) {
        return this.setWickedness(this.getCurrentWickedness() + wickednessAmount);
    }

    public void setWickednessData(WickednessData wickednessData) {
        this.wickednessData = wickednessData;
    }

    public void syncToClient(ServerPlayer player) {
        CompoundTag tag = wickednessData.serializeNBT(player.registryAccess());
        Networking.sendToPlayerClient(new PacketUpdateWickedness(tag), player);
    }
}

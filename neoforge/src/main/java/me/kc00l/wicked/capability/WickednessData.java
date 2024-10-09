package me.kc00l.wicked.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class WickednessData implements INBTSerializable<CompoundTag> {
    private int wickedness;
    private int maxWickedness;

    public int getWickedness() {
        return wickedness;
    }

    public void setWickedness(int wickedness) {
        this.wickedness = wickedness;
    }

    public void setMaxWickedness(int maxWickedness) {
        this.maxWickedness = maxWickedness;
    }

    public int getMaxWickedness() {
        return maxWickedness;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("current", getWickedness());
        tag.putDouble("max", getMaxWickedness());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        setWickedness(tag.getByte("current"));
        setMaxWickedness(tag.getByte("max"));
    }
}

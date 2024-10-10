package me.kc00l.wicked.common.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class WickednessData implements INBTSerializable<CompoundTag> {
    private int wickedness;
    private int maxWickedness;
    private boolean wickednessDecay;

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
        tag.putBoolean("decay", doesWickednessDecay());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        setWickedness(tag.getByte("current"));
        setMaxWickedness(tag.getByte("max"));
        setWickednessDecay(tag.getBoolean("decay"));
    }

    public boolean doesWickednessDecay() {
        return wickednessDecay;
    }

    public void setWickednessDecay(boolean wickednessDecay) {
        this.wickednessDecay = wickednessDecay;
    }
}

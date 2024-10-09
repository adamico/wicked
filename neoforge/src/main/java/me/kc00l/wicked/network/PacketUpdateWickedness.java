package me.kc00l.wicked.network;

import me.kc00l.wicked.ModNeoForge;
import me.kc00l.wicked.capability.WickednessData;
import me.kc00l.wicked.setup.registry.AttachmentsRegistry;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import me.kc00l.wicked.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PacketUpdateWickedness extends AbstractPacket {
    CompoundTag tag;

    public PacketUpdateWickedness(CompoundTag tag) {
        this.tag = tag;
    }

    //Decoder
    public PacketUpdateWickedness(RegistryFriendlyByteBuf buf) {
        this.tag = buf.readNbt();
    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeNbt(this.tag);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        WickednessData data = new WickednessData();
        data.deserializeNBT(player.registryAccess(), this.tag);
        player.setData(AttachmentsRegistry.WICKEDNESS_ATTACHMENT, data);
        var cap = CapabilityRegistry.getWickedness(ModNeoForge.proxy.getPlayer());
        if (cap != null) {
            cap.setWickednessData(data);
        }
    }

    public static final Type<PacketUpdateWickedness> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "update_wickedness"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketUpdateWickedness> CODEC =
            StreamCodec.ofMember(PacketUpdateWickedness::toBytes, PacketUpdateWickedness::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

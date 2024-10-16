package me.kc00l.wickedness.common.item.custom;

import me.kc00l.wickedness.common.capability.WickednessCap;
import me.kc00l.wickedness.setup.registry.CapabilityRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class KarmaCompassItem extends Item {
    public KarmaCompassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (level.isClientSide) return InteractionResultHolder.fail(itemstack);

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        int currentWickedness = wickednessCap.getCurrentWickedness();

        player.sendSystemMessage(Component.translatable("command.wickedness.current_wickedness", currentWickedness));
        itemstack.hurtAndBreak(1, ((ServerLevel) level), player,
                item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

        level.playSound(null, player.blockPosition(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS);

        return InteractionResultHolder.success(itemstack);

    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String translatableKey = (Screen.hasShiftDown()) ? "tooltip.wickedness.karma_compass.shift_down" : "tooltip.wickedness.karma_compass";
        tooltipComponents.add(Component.translatable(translatableKey));

        super.appendHoverText(pStack, context, tooltipComponents, tooltipFlag);
    }
}

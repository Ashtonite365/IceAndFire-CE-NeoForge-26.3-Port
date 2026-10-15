package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.data.TrollType;
import com.iafenvoy.iceandfire.registry.IafTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class TrollWeaponItem extends Item {
    public final TrollType.ITrollWeapon weapon;

    public TrollWeaponItem(TrollType.ITrollWeapon weapon) {
        super(new Properties().sword(IafTiers.TROLL_WEAPON_TOOL_MATERIAL, 15, -3.5F));
        this.weapon = weapon;
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player)
            return;
        super.hurtEnemy(stack, target, attacker);
    }

    // 26.3: swingTime/attackAnim are no longer accessible (LivingEntity.SwingState is private), so the old
    // inventoryTick swing slowdown is dropped; an uncharged swing is cancelled through NeoForge's onEntitySwing hook instead.
    @Override
    public boolean onEntitySwing(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        return entity instanceof Player player && player.getAttackStrengthScale(0) < 1 && player.isSwinging();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NonNull Consumer<Component> tooltip, @NotNull TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        tooltip.accept(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
    }
}

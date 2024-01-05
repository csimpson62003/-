package net.caleb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.caleb.Items.CustomItems.MetalFishingRod_Item;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberMixin extends Entity {

    @Shadow public abstract PlayerEntity getPlayerOwner();

    private FishingBobberMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * Patches {@link FishingBobberEntity#removeIfInvalid(PlayerEntity)} to work for all items of the type {@link FishingRodItem}.
     *
     * @param playerEntity  owner of this {@link FishingBobberEntity}
     * @param cir  mixin callback info
     */
    @Inject(
            method = "removeIfInvalid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeIfInvalid(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = playerEntity.getMainHandStack();
        ItemStack offHandStack = playerEntity.getOffHandStack();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof MetalFishingRod_Item;
        boolean offHandHasRod = offHandStack.getItem() instanceof MetalFishingRod_Item;

        if (!playerEntity.isRemoved() && playerEntity.isAlive() && (mainHandHasRod || offHandHasRod) && this.squaredDistanceTo(playerEntity) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }
}
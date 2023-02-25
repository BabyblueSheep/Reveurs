package net.babybluesheep.reveurs.core.mixin;

import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.common.interaction.DripstonePierceInteraction;
import net.babybluesheep.reveurs.core.data.InteractionsDataLoader;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {
    @Inject(at = @At("HEAD"),
            method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z")
    private void FallOnItemStack(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if(((FallingBlockEntity)(Object)this).getBlockState().getBlock() == Blocks.POINTED_DRIPSTONE) {
            ((FallingBlockEntity)(Object)this).world.getOtherEntities(((FallingBlockEntity)(Object)this), ((FallingBlockEntity)(Object)this).getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR).forEach((entity) -> {
                if ( (entity instanceof ItemEntity itemEntity) ) {
                    for(DripstonePierceInteraction i : InteractionsDataLoader.DRIPSTONE_INTERACTIONS) {
                        if(itemEntity.getStack().isOf(i.getInput()) && itemEntity.getStack().getCount() >= i.getMinCount()) {
                            i.interactHappen(itemEntity.world, itemEntity);
                        }
                    }
                }
            });
        }
    }
}

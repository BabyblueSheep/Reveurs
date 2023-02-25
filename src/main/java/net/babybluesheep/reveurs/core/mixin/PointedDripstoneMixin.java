package net.babybluesheep.reveurs.core.mixin;

import net.babybluesheep.reveurs.common.interaction.DripstonePierceInteraction;
import net.babybluesheep.reveurs.core.data.InteractionsDataLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneMixin {
    @Inject(at = @At("HEAD"),
            method = "onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V")
    private void FallOnItemStack(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity instanceof ItemEntity itemEntity) {
            for(DripstonePierceInteraction i : InteractionsDataLoader.DRIPSTONE_INTERACTIONS) {
                if(itemEntity.getStack().isOf(i.getInput()) && itemEntity.getStack().getCount() >= i.getMinCount()) {
                    i.interactHappen(world, itemEntity);
                }
            }
        }
    }
}
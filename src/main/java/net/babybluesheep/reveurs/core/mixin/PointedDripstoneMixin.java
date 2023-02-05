package net.babybluesheep.reveurs.core.mixin;

import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.core.data.InteractionsDataLoader;
import net.babybluesheep.reveurs.core.registry.ReveursItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneMixin {
    @Inject(at = @At("HEAD"), method = "onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V")
    private void FallOnItemStack(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        /*if (entity instanceof ItemEntity) {
            boolean interactExists = false;
            HashMap<ItemStack, ItemStack> inputOutput = InteractionsDataLoader.INTERACTIONS.get( new Identifier("reveurs:dripstone_pierce") );

            ItemStack inputStack = ((ItemEntity)entity).getStack();
            ItemStack baseStack = null;

            for(ItemStack i : inputOutput.keySet() ) {
                if (inputStack.isOf(i.getItem())) {
                    interactExists = true;
                    baseStack = i;
                    break;
                }
            }

            if(interactExists) {
                int count = 0;
                int maxCount = inputStack.getCount();
                for (int i = 0; i < maxCount; i += baseStack.getCount()) {
                    count += inputOutput.get(baseStack).getCount();
                    inputStack.setCount(inputStack.getCount() - inputOutput.get(baseStack).getCount());
                }
                ItemStack outputStack = new ItemStack(inputOutput.get(baseStack).getItem(), count);
                ItemEntity outputEntity = new ItemEntity(EntityType.ITEM, world);
                outputEntity.setStack(outputStack);
                outputEntity.setPosition(entity.getPos());
                outputEntity.setVelocity(entity.getVelocity().x/2, 0.3, entity.getVelocity().z/2);
            }
        }*/
    }
}
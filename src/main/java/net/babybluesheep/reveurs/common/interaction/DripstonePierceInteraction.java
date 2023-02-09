package net.babybluesheep.reveurs.common.interaction;

import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.core.helper.WorldHelper;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class DripstonePierceInteraction implements Interaction {
    @NotNull protected final Item input;
    protected final int minCount;
    @NotNull protected final Map<ItemStack, Float> output;

    protected final float exp;
    @NotNull protected final SoundEvent sound;

    @NotNull protected final Identifier id;

    public DripstonePierceInteraction(@NotNull Identifier id, @NotNull Item input, int minCount, @NotNull Map<ItemStack, Float> output, float exp, @NotNull SoundEvent sound) {
        this.id = id;

        this.input = input;
        this.minCount = Math.max(minCount, 1);
        this.output = output;

        this.exp = exp;
        this.sound = sound;
    }


    @Override
    public void interactHappen(@NotNull World world, @NotNull ItemEntity inputEntity) {
        float finalExp = 0;
        int count = 0;
        int maxCount = inputEntity.getStack().getCount();

        do {
            maxCount -= minCount;
            count += minCount;
            inputEntity.getStack().setCount(inputEntity.getStack().getCount() - minCount);
        } while (maxCount >= minCount);

        for(ItemStack i : output.keySet()) {
            ItemStack outputStack = new ItemStack(i.getItem(), 0);
            for(int j = 1; j <= i.getCount()*count/minCount; j++) {
                Random rand = new Random();
                if(rand.nextFloat() < output.get(i)) {
                    outputStack.setCount(outputStack.getCount() + 1);
                    finalExp += exp;
                }
            }
            ItemEntity outputEntity = new ItemEntity(EntityType.ITEM, world);
            outputEntity.setStack(outputStack);
            outputEntity.setPosition(inputEntity.getPos());
            outputEntity.setVelocity(inputEntity.getVelocity().x/2, -inputEntity.getVelocity().y/2, inputEntity.getVelocity().z/2);
            world.spawnEntity(outputEntity);
        }

        if (world instanceof ServerWorld serverWorld) {
            WorldHelper.dropExperience(serverWorld, inputEntity.getPos(), 1, finalExp);
        }

        if (!world.isClient) {
            world.playSound(null, inputEntity.getBlockPos(), sound, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    @Override
    public @NotNull Item getInput() {
        return this.input;
    }

    @Override
    public int GetMinCount() {
        return this.minCount;
    }

    @Override
    public @NotNull Map<ItemStack, Float> getOutput() {
        return this.output;
    }

    @Override
    public @NotNull Identifier getIdentifier() {
        return this.id;
    }

    @Override
    public float getExp() {
        return this.exp;
    }

    @Override
    public @NotNull SoundEvent getSound() {
        return this.sound;
    }


}

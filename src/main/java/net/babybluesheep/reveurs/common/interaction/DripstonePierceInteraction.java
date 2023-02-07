package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DripstonePierceInteraction implements Interaction {
    @NotNull protected final Item input;
    protected final int minCount;
    @NotNull protected final List<ItemStack> output;

    @NotNull protected final Identifier id;

    public DripstonePierceInteraction(@NotNull Identifier id, @NotNull Item input, int minCount, @NotNull List<ItemStack> output) {
        this.id = id;

        this.input = input;
        this.minCount = Math.max(minCount, 1);
        this.output = output;
    }


    @Override
    public void interactHappen(@NotNull World world, @NotNull ItemEntity inputEntity) {
        int count = 0;
        int maxCount = inputEntity.getStack().getCount();

        do {
            maxCount -= minCount;
            count += minCount;
            inputEntity.getStack().setCount(inputEntity.getStack().getCount() - minCount);
        } while (maxCount >= minCount);

        for(ItemStack i : output) {
            ItemStack outputStack = new ItemStack(i.getItem(), i.getCount()*count/minCount);
            ItemEntity outputEntity = new ItemEntity(EntityType.ITEM, world);
            outputEntity.setStack(outputStack);
            outputEntity.setPosition(inputEntity.getPos());
            outputEntity.setVelocity(inputEntity.getVelocity().x/2, -inputEntity.getVelocity().y/2, inputEntity.getVelocity().z/2);
            world.spawnEntity(outputEntity);
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
    public @NotNull List<ItemStack> getOutput() {
        return this.output;
    }

    @Override
    public @NotNull Identifier getIdentifier() {
        return this.id;
    }
}

package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class DripstonePierceInteraction implements Interaction {
    public final Item input;
    protected final int minCount;
    protected final List<ItemStack> output;

    protected final Identifier id;

    public DripstonePierceInteraction(Identifier id, Item input, int minCount, List<ItemStack> output) {
        this.id = id;

        this.input = input;
        this.minCount = minCount;
        this.output = output;
    }


    @Override
    public void interactHappen(World world, ItemEntity inputEntity) {
        int count = 0;
        int maxCount = inputEntity.getStack().getCount();
        for (int i = 0; i < maxCount; i += minCount) {
            count += minCount;
            inputEntity.getStack().setCount(inputEntity.getStack().getCount() - minCount);
        }
        for(ItemStack i : output) {
            ItemStack outputStack = new ItemStack(i.getItem(), count);
            ItemEntity outputEntity = new ItemEntity(EntityType.ITEM, world);
            outputEntity.setStack(outputStack);
            outputEntity.setPosition(inputEntity.getPos());
            outputEntity.setVelocity(inputEntity.getVelocity().x/2, 0.3, inputEntity.getVelocity().z/2);
            world.spawnEntity(outputEntity);
        }
        inputEntity.kill();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }
}

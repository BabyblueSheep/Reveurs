package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DripstonePierceInteraction implements Interaction {
    protected final ItemEntity input;
    protected final int minCount;
    protected final ItemStack output;

    protected final Identifier id;

    DripstonePierceInteraction(Identifier id, ItemEntity input, int minCount, ItemStack output) {
        this.id = id;

        this.input = input;
        this.minCount = minCount;
        this.output = output;
    }


    @Override
    public void interactHappen(World world) {
        int count = 0;
        int maxCount = input.getStack().getCount();
        for (int i = 0; i < maxCount; i += minCount) {
            count += minCount;
            input.getStack().setCount(input.getStack().getCount() - minCount);
        }
        ItemStack outputStack = new ItemStack(output.getItem(), count);
        ItemEntity outputEntity = new ItemEntity(EntityType.ITEM, world);
        outputEntity.setStack(outputStack);
        outputEntity.setPosition(input.getPos());
        outputEntity.setVelocity(input.getVelocity().x/2, 0.3, input.getVelocity().z/2);
        world.spawnEntity(outputEntity);
        input.kill();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }
}

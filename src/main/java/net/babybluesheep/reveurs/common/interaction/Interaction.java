package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public interface Interaction {

    Item getInput();
    int GetMinCount();
    Map<ItemStack, Float> getOutput();

    Identifier getIdentifier();

    float getExp();

    void interactHappen(World world, ItemEntity inputEntity);
}

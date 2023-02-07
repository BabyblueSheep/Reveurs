package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public interface Interaction {

    Item getInput();
    int GetMinCount();
    List<ItemStack> getOutput();

    Identifier getIdentifier();

    void interactHappen(World world, ItemEntity inputEntity);
}

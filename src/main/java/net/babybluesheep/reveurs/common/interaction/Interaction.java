package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface Interaction {
    void interactHappen(World world, ItemEntity inputEntity);

    Identifier getId();
}

package net.babybluesheep.reveurs.common.interaction;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface Interaction {
    void interactHappen(World world);

    Identifier getId();
}

package net.babybluesheep.reveurs.core.helper;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WorldHelper {
    public static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }
}
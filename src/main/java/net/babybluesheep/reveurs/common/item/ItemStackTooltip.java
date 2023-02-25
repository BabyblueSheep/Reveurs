package net.babybluesheep.reveurs.common.item;

import org.spongepowered.asm.mixin.Unique;

public interface ItemStackTooltip {
    public String reveurs$getCustomTooltip();
    public void reveurs$setCustomTooltip(String reveurs$customTooltip);
}

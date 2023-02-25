package net.babybluesheep.reveurs.core.mixin;

import net.babybluesheep.reveurs.common.item.ItemStackTooltip;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemStackTooltip {



    @Override
    public String reveurs$getCustomTooltip() {
        return ((ItemStack)(Object)this).getOrCreateNbt().getString("reveurs.tooltip");
    }

    @Override
    public void reveurs$setCustomTooltip(String tooltip) {
        ((ItemStack)(Object)this).getOrCreateNbt().putString("reveurs.tooltip", tooltip);
    }



    @Inject(at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/item/ItemStack;isSectionVisible(ILnet/minecraft/item/ItemStack$TooltipSection;)Z"),
            method = "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;",
            locals = LocalCapture.CAPTURE_FAILHARD,
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getHideFlags()I"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V")
            ))
    private void addCustomTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, List<Text> list) {
        if(reveurs$getCustomTooltip() != null && !reveurs$getCustomTooltip().isBlank()) {
            list.add(Text.literal(reveurs$getCustomTooltip()));
        }

    }
}

package net.babybluesheep.reveurs.core.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.common.interaction.DripstonePierceInteraction;
import net.babybluesheep.reveurs.core.data.InteractionsDataLoader;
import net.babybluesheep.reveurs.core.registry.ReveursItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Supplier;

public class ReveursModEMI implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        for(DripstonePierceInteraction i : InteractionsDataLoader.DRIPSTONE_INTERACTIONS) {
            EmiWorldInteractionRecipe.Builder emiDripstoneRecipe = EmiWorldInteractionRecipe.builder()
                    .id(i.getIdentifier())
                    .leftInput(EmiStack.of(i.getInput(), i.getMinCount()))
                    .rightInput(EmiStack.of(Items.POINTED_DRIPSTONE), true);
            for(Map.Entry<ItemStack, Float> j : i.getOutput().entrySet()) {
                emiDripstoneRecipe.output(EmiStack.of(j.getKey()));
            }
            addRecipeSafe(registry, emiDripstoneRecipe::build);
        }
    }

    private static void addRecipeSafe(EmiRegistry registry, Supplier<EmiRecipe> supplier) {
        try {
            registry.addRecipe(supplier.get());
        } catch (Throwable e) {
            ReveursMod.LOGGER.warn("Exception when parsing EMI Reveurs recipe (no ID available)");
        }
    }
}

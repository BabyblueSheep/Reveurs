package net.babybluesheep.reveurs.core.data;

import com.google.gson.*;
import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.common.interaction.DripstonePierceInteraction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionsDataLoader extends JsonDataLoader {
    private static final Gson GSON = (new GsonBuilder()).create();

    public static final List<DripstonePierceInteraction> DRIPSTONE_INTERACTIONS = new ArrayList<>();

    public InteractionsDataLoader() {
        super(GSON, "interactions");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        DRIPSTONE_INTERACTIONS.clear();

        for(JsonElement entry : prepared.values()) {
            JsonObject object = entry.getAsJsonObject();

            String idString = object.getAsJsonPrimitive("type").getAsString();
            Identifier identifier = new Identifier(idString);

            ReveursMod.LOGGER.info(identifier.toString());
        }
    }
}

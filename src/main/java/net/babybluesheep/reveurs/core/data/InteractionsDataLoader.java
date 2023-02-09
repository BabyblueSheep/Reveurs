package net.babybluesheep.reveurs.core.data;

import com.google.gson.*;
import net.babybluesheep.reveurs.ReveursMod;
import net.babybluesheep.reveurs.common.interaction.DripstonePierceInteraction;
import net.babybluesheep.reveurs.core.helper.ArrayHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.profiler.Profiler;

import java.util.*;

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

            String fileName = Objects.requireNonNull(ArrayHelper.getKeyByValue(prepared, object)).toString();
            String[] splitFileName = fileName.split(":");
            String fileNameNoID = splitFileName[splitFileName.length - 1];

            JsonPrimitive idPrimitive = object.getAsJsonPrimitive("type");
            if(idPrimitive == null) {
                ReveursMod.LOGGER.warn("Interaction type in " + fileName + " not found, skipping");
                continue;
            }
            String idString = idPrimitive.getAsString();

            Identifier identifier = new Identifier(idString + "/" + fileNameNoID);

            JsonObject input = object.getAsJsonObject("input");
            if(input == null) {
                ReveursMod.LOGGER.warn("Input in " + fileName + " not found, skipping");
                continue;
            }
            if(isItemObjectInvalid(input)) {
                ReveursMod.LOGGER.warn("Input item stack in " + fileName + " is invalid, skipping");
                continue;
            }
            Identifier inputItem = new Identifier(input.getAsJsonPrimitive("item").getAsString());
            int inputCount = input.getAsJsonPrimitive("count").getAsInt();

            List<JsonElement> outputs = object.getAsJsonArray("output").asList();
            if(object.getAsJsonArray("output") == null) {
                ReveursMod.LOGGER.warn("Output in " + fileName + " not found, skipping");
                continue;
            }
            Map<ItemStack, Float> itemOutputs = new HashMap<>();
            for (JsonElement i : outputs) {
                JsonObject iOutput = i.getAsJsonObject();
                if(isItemObjectInvalid(i.getAsJsonObject())) {
                    ReveursMod.LOGGER.warn("Output item stack in " + fileName + " is invalid, skipping");
                    continue;
                }

                Identifier outputIDi = new Identifier(iOutput.getAsJsonPrimitive("item").getAsString());
                int outputCounti = iOutput.getAsJsonPrimitive("count").getAsInt();
                ItemStack outputStack = new ItemStack(Registries.ITEM.get(outputIDi), outputCounti);

                float outputChance = iOutput.has("chance") ? Math.max(0f, Math.min(1f, iOutput.getAsJsonPrimitive("chance").getAsFloat())) : 1f;

                itemOutputs.put(outputStack, outputChance);
            }

            float expObject = object.has("experience") ? object.getAsJsonPrimitive("experience").getAsFloat() : 0f;

            String soundID = object.has("sound") ? object.getAsJsonPrimitive("sound").getAsString() : "";
            SoundEvent sound = Registries.SOUND_EVENT.get(new Identifier(soundID));

            switch (idString) {
                case "reveurs:dripstone_pierce" -> {
                    DripstonePierceInteraction interact = new DripstonePierceInteraction(identifier, Registries.ITEM.get(inputItem), inputCount, itemOutputs, expObject, sound);
                    DRIPSTONE_INTERACTIONS.add(interact);
                }
                default -> ReveursMod.LOGGER.warn("Interaction type in " + identifier + " invalid, skipping");
            }
        }
    }

    private boolean isItemObjectInvalid(JsonObject object) {
        Identifier jsonItem = new Identifier(object.getAsJsonPrimitive("item").getAsString());
        if(Registries.ITEM.containsId(jsonItem)) {
            return false;
        }
        return object.getAsJsonPrimitive("count") == null || object.getAsJsonPrimitive("item") == null;
    }
}

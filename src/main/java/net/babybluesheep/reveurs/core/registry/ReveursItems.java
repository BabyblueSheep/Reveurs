package net.babybluesheep.reveurs.core.registry;

import net.babybluesheep.reveurs.ReveursMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ReveursItems {
    public static final Item BISMUTH = new Item(new FabricItemSettings());
    public static final Item REFINED_BISMUTH = new Item(new FabricItemSettings());
    public static final Item GEODE = new Item(new FabricItemSettings());

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(ReveursMod.MOD_ID, "bismuth"), BISMUTH);
        Registry.register(Registries.ITEM, new Identifier(ReveursMod.MOD_ID, "refined_bismuth"), REFINED_BISMUTH);
        Registry.register(Registries.ITEM, new Identifier(ReveursMod.MOD_ID, "geode"), GEODE);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.addAfter(Items.NETHERITE_INGOT, GEODE, BISMUTH, REFINED_BISMUTH);
        });
    }
}
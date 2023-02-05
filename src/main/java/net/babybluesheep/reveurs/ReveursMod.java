package net.babybluesheep.reveurs;

import net.babybluesheep.reveurs.core.data.InteractionsDataLoader;
import net.babybluesheep.reveurs.core.registry.ReveursItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReveursMod implements ModInitializer {
	public static final String MOD_ID = "reveurs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("I'm here!!!!!!!!!!");

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new InteractionsDataLoaderImpl());

		ReveursItems.registerItems();
	}

	public static class InteractionsDataLoaderImpl extends InteractionsDataLoader implements SimpleSynchronousResourceReloadListener {
		@Override
		public Identifier getFabricId() {
			return new Identifier(MOD_ID, "interactions");
		}

		@Override
		public void reload(ResourceManager manager) { }
	}
}

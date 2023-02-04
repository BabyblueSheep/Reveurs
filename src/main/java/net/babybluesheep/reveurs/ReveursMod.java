package net.babybluesheep.reveurs;

import net.babybluesheep.reveurs.core.registry.ReveursItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReveursMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("reveurs");
	public static final String MOD_ID = "reveurs";

	@Override
	public void onInitialize() {
		ReveursItems.registerItems();
	}
}

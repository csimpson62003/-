package net.caleb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.caleb.Items.ModItemGroups;
import net.caleb.Items.ModItems;
import net.fabricmc.api.ModInitializer;

public class Fishersmod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	public static final String MOD_ID = "fishersmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		
		LOGGER.info("Hello Fabric world!");
	}
}
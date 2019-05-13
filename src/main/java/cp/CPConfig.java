package cp;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cp.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CPConfig
{
	public static final Logger logger = LogManager.getLogger(Reference.NAME);
	public static CPConfig CONFIG = new CPConfig();
	public static Configuration config;


//	public int cannonShotCost;

	public void init(Configuration config)
	{
//		this.cannonShotCost= config.getInt("cp.config.shotCost", "all", 32, 0, 255, "");
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
		{
			CPConfig.syncConfig();
		}
	}

	public static void loadConfig()
	{
		File configFile = new File(Loader.instance().getConfigDir(), "cashingpower2.cfg");
		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			} catch (Exception e)
			{
				logger.warn("Could not create a TacticalFrame config file.");
				logger.warn(e.getLocalizedMessage());
			}
		}
		config = new Configuration(configFile);
		config.load();
	}

	public static void syncConfig()
	{
		CONFIG.init(config);
		config.save();
	}
}

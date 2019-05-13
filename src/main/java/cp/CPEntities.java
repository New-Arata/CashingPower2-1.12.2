package cp;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CPEntities {

	public static void init() {

		for (Biome biome : Biome.REGISTRY)
		{
			if (biome != null && isSpawnableBiomeType(biome))
			{
			}
		}
	}

	private static boolean isSpawnableBiomeType(Biome biome)
	{
		if (BiomeDictionary.hasType(biome, Type.NETHER))
		{
			return false;
		}
		if (BiomeDictionary.hasType(biome, Type.MUSHROOM))
		{
			return false;
		}
		if (BiomeDictionary.hasType(biome, Type.END))
		{
			return false;
		}
		return true;
	}
}

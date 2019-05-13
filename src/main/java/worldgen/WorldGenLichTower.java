package worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenLichTower implements IWorldGenerator
{
	private final boolean isForced;
	private int range = -1;
	private int forceX = 0;
	private int forceZ = 0;

	private static Random pRandom;

	public WorldGenLichTower(boolean force) {
		super();
		isForced = force;
	}
	public void setForcePos(int x, int z) {
		forceX = x;
		forceZ = z;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
//		generateWindmill(random, chunkZ, chunkZ, world, chunkGenerator, chunkProvider);
	}

}

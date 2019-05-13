package cp;

import cp.advancements.HasPlayerMP;
import cp.advancements.HasPlayerMP2;
import cp.advancements.HasPlayerMP3;
import net.minecraft.advancements.CriteriaTriggers;

public class CPAdvancements
{
	public static final HasPlayerMP HAS_PLAYER_MP_1 = CriteriaTriggers.register(new HasPlayerMP());
	public static final HasPlayerMP2 HAS_PLAYER_MP_2 = CriteriaTriggers.register(new HasPlayerMP2());
	public static final HasPlayerMP3 HAS_PLAYER_MP_3 = CriteriaTriggers.register(new HasPlayerMP3());

	public static void init() {}
}

package cp.util.handlers;

import net.minecraft.world.World;

public class CPWorldTimeHandler
{
    public static int getHour(World world)
    {

        long t = world.getWorldInfo().getWorldTime() % 24000;
        t += 6000;
        if (t >= 24000) t -= 24000;

        return (int) (t / 1000);

    }

    public static int getMinute(World world)
    {

        long t = world.getWorldInfo().getWorldTime() % 24000;
        t += 6000;
        if (t >= 24000) t -= 24000;
        if (t >= 12000) {
            t -= 12000;
        }

        return (int) ((t % 1000) / (1000f / 60f));
    }
}

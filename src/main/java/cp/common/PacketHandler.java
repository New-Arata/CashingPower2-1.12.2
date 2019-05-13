package cp.common;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("kiritancannon");

	public static void init()
	{
		INSTANCE.registerMessage(MessageKeyPressedHandler.class, MessageKeyPressed.class, 0, Side.SERVER);
	}
}

package cp;

import cp.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class CPSoundEvents{

    public static final SoundEvent MPCOIN = createEvent("cp.coin");

    public static SoundEvent createEvent(String sound)
    {
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, sound);
		return new SoundEvent(name).setRegistryName(name);
	}

    @SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
		evt.getRegistry().register(MPCOIN);
	}


}
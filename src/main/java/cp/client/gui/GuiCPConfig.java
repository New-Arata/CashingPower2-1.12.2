package cp.client.gui;

import cp.CPConfig;
import cp.util.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiCPConfig extends GuiConfig {
    public GuiCPConfig(GuiScreen parent) {
        super(parent, new ConfigElement(CPConfig.config.getCategory("all")).getChildElements(), Reference.MOD_ID, false, false, "KiritanCannon Config");
        titleLine2 = CPConfig.config.getConfigFile().getAbsolutePath();
    }

   @Override
    public void onGuiClosed(){
        super.onGuiClosed();
    }
}
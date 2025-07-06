package de.icor;

import de.icor.gui.GuiManager;
import lombok.Getter;
import lombok.Setter;

public final class Gui {
    @Setter
    @Getter
    private static GuiManager guiManager = new GuiManager();

}

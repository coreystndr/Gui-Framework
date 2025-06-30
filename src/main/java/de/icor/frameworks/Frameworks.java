package de.icor.frameworks;

import de.icor.frameworks.gui.*;
import lombok.Getter;
import lombok.Setter;

public final class Frameworks {
    @Setter
    @Getter
    private static GuiManager guiManager = new GuiManager();
}

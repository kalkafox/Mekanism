package mekanism.client.gui.element.window;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Collections;
import mekanism.api.RelativeSide;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.button.ColorButton;
import mekanism.client.gui.element.button.MekanismImageButton;
import mekanism.client.gui.element.button.SideDataButton;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.SelectedWindowData;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.network.to_server.PacketConfigurationUpdate;
import mekanism.common.network.to_server.PacketConfigurationUpdate.ConfigurationPacket;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.interfaces.ISideConfiguration;
import mekanism.common.util.text.BooleanStateDisplay.OnOff;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class GuiTransporterConfig<TILE extends TileEntityMekanism & ISideConfiguration> extends GuiWindow {

    private final TILE tile;

    public GuiTransporterConfig(IGuiWrapper gui, int x, int y, TILE tile) {
        super(gui, x, y, 156, 95, SelectedWindowData.UNSPECIFIED);
        this.tile = tile;
        interactionStrategy = InteractionStrategy.ALL;
        addChild(new GuiInnerScreen(gui, relativeX + 41, relativeY + 15, 74, 12,
              () -> Collections.singletonList(MekanismLang.STRICT_INPUT_ENABLED.translate(OnOff.of(tile.getEjector().hasStrictInput())))));
        addChild(new GuiSlot(SlotType.NORMAL, gui, relativeX + 111, relativeY + 48));
        addChild(new MekanismImageButton(gui, gui.getLeft() + relativeX + 136, gui.getTop() + relativeY + 6, 14, 16, getButtonLocation("exclamation"),
              () -> Mekanism.packetHandler.sendToServer(new PacketConfigurationUpdate(this.tile.getBlockPos())), getOnHover(MekanismLang.STRICT_INPUT)));
        addChild(new ColorButton(gui, gui.getLeft() + relativeX + 112, gui.getTop() + relativeY + 49, 16, 16,
              () -> this.tile.getEjector().getOutputColor(),
              () -> Mekanism.packetHandler.sendToServer(new PacketConfigurationUpdate(this.tile.getBlockPos(), Screen.hasShiftDown() ? 2 : 0)),
              () -> Mekanism.packetHandler.sendToServer(new PacketConfigurationUpdate(this.tile.getBlockPos(), 1))));
        addSideDataButton(RelativeSide.BOTTOM, 44, 64);
        addSideDataButton(RelativeSide.TOP, 44, 34);
        addSideDataButton(RelativeSide.FRONT, 44, 49);
        addSideDataButton(RelativeSide.BACK, 29, 64);
        addSideDataButton(RelativeSide.LEFT, 29, 49);
        addSideDataButton(RelativeSide.RIGHT, 59, 49);
        Mekanism.packetHandler.sendToServer(new PacketGuiInteract(GuiInteraction.CONTAINER_TRACK_EJECTOR, this.tile, MekanismContainer.TRANSPORTER_CONFIG_WINDOW));
        ((MekanismContainer) ((GuiMekanism<?>) gui()).getMenu()).startTracking(MekanismContainer.TRANSPORTER_CONFIG_WINDOW, this.tile.getEjector());
    }

    private void addSideDataButton(RelativeSide side, int xPos, int yPos) {
        SideDataButton button = addChild(new SideDataButton(gui(), getGuiLeft() + relativeX + xPos, getGuiTop() + relativeY + yPos, side,
              () -> tile.getConfig().getDataType(TransmissionType.ITEM, side), () -> tile.getEjector().getInputColor(side), tile, () -> null,
              ConfigurationPacket.INPUT_COLOR, getOnHover(side)));
        if (!tile.getEjector().isInputSideEnabled(side)) {
            button.active = false;
        }
    }

    @Override
    public void close() {
        super.close();
        Mekanism.packetHandler.sendToServer(new PacketGuiInteract(GuiInteraction.CONTAINER_STOP_TRACKING, tile, MekanismContainer.TRANSPORTER_CONFIG_WINDOW));
        ((MekanismContainer) ((GuiMekanism<?>) gui()).getMenu()).stopTracking(MekanismContainer.TRANSPORTER_CONFIG_WINDOW);
    }

    private IHoverable getOnHover(RelativeSide side) {
        return (onHover, matrix, xAxis, yAxis) -> {
            if (onHover instanceof SideDataButton) {
                SideDataButton button = (SideDataButton) onHover;
                DataType dataType = button.getDataType();
                if (dataType != null) {
                    EnumColor color = button.getColor();
                    ITextComponent colorComponent = color == null ? MekanismLang.NONE.translate() : color.getColoredName();
                    displayTooltip(matrix, MekanismLang.GENERIC_WITH_PARENTHESIS.translate(colorComponent, side), xAxis, yAxis);
                }
            }
        };
    }

    @Override
    public void renderForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.renderForeground(matrix, mouseX, mouseY);
        drawTitleText(matrix, MekanismLang.TRANSPORTER_CONFIG.translate(), 5);
        drawString(matrix, MekanismLang.INPUT.translate(), relativeX + 38, relativeY + 81, subheadingTextColor());
        drawString(matrix, MekanismLang.OUTPUT.translate(), relativeX + 104, relativeY + 68, subheadingTextColor());
    }
}

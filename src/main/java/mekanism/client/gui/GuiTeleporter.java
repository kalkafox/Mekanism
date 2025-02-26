package mekanism.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.button.ColorButton;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.gui.element.button.TranslationButton;
import mekanism.client.gui.element.custom.GuiTeleporterStatus;
import mekanism.client.gui.element.scroll.GuiTextScrollList;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.gui.element.text.BackgroundType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.client.gui.element.text.InputValidator;
import mekanism.client.gui.element.window.GuiConfirmationDialog;
import mekanism.client.gui.element.window.GuiConfirmationDialog.DialogType;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.content.teleporter.TeleporterFrequency;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.lib.frequency.Frequency;
import mekanism.common.lib.frequency.Frequency.FrequencyIdentity;
import mekanism.common.lib.frequency.FrequencyManager;
import mekanism.common.lib.frequency.FrequencyType;
import mekanism.common.network.to_server.PacketGuiSetFrequency;
import mekanism.common.network.to_server.PacketGuiSetFrequency.FrequencyUpdate;
import mekanism.common.network.to_server.PacketTeleporterSetColor;
import mekanism.common.tile.TileEntityTeleporter;
import mekanism.common.util.text.OwnerDisplay;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiTeleporter extends GuiMekanismTile<TileEntityTeleporter, MekanismTileContainer<TileEntityTeleporter>> {

    private MekanismButton publicButton;
    private MekanismButton privateButton;
    private MekanismButton setButton;
    private MekanismButton deleteButton;
    private GuiTextScrollList scrollList;
    private GuiTextField frequencyField;
    private boolean privateMode;

    private boolean init = false;

    public GuiTeleporter(MekanismTileContainer<TileEntityTeleporter> container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        TeleporterFrequency teleporterFrequency = tile.getFrequency(FrequencyType.TELEPORTER);
        if (teleporterFrequency != null) {
            privateMode = teleporterFrequency.isPrivate();
        }
        imageHeight += 64;
        titleLabelY = 4;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addButton(new GuiTeleporterStatus(this, () -> tile.getFrequency(FrequencyType.TELEPORTER) != null, () -> tile.status));
        addButton(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 158, 26));
        scrollList = addButton(new GuiTextScrollList(this, 27, 36, 122, 42));

        publicButton = addButton(new TranslationButton(this, leftPos + 27, topPos + 14, 60, 20, MekanismLang.PUBLIC, () -> {
            privateMode = false;
            updateButtons();
        }));
        privateButton = addButton(new TranslationButton(this, leftPos + 89, topPos + 14, 60, 20, MekanismLang.PRIVATE, () -> {
            privateMode = true;
            updateButtons();
        }));
        setButton = addButton(new TranslationButton(this, leftPos + 27, topPos + 120, 50, 18, MekanismLang.BUTTON_SET, () -> {
            int selection = scrollList.getSelection();
            if (selection != -1) {
                Frequency freq = privateMode ? tile.getPrivateCache(FrequencyType.TELEPORTER).get(selection) : tile.getPublicCache(FrequencyType.TELEPORTER).get(selection);
                setFrequency(freq.getName());
            }
            updateButtons();
        }));
        deleteButton = addButton(new TranslationButton(this, leftPos + 79, topPos + 120, 50, 18, MekanismLang.BUTTON_DELETE,
              () -> GuiConfirmationDialog.show(this, MekanismLang.FREQUENCY_DELETE_CONFIRM.translate(), () -> {
                  int selection = scrollList.getSelection();
                  if (selection != -1) {
                      Frequency freq = privateMode ? tile.getPrivateCache(FrequencyType.TELEPORTER).get(selection) : tile.getPublicCache(FrequencyType.TELEPORTER).get(selection);
                      Mekanism.packetHandler.sendToServer(PacketGuiSetFrequency.create(FrequencyUpdate.REMOVE_TILE, FrequencyType.TELEPORTER, freq.getIdentity(), tile.getBlockPos()));
                      scrollList.clearSelection();
                  }
                  updateButtons();
              }, DialogType.DANGER)));
        addButton(new GuiSlot(SlotType.NORMAL, this, 131, 120).setRenderAboveSlots());
        addButton(new ColorButton(this, leftPos + 132, topPos + 121, 16, 16, () -> {
            TeleporterFrequency frequency = getFrequency();
            return frequency == null ? null : frequency.getColor();
        }, () -> sendColorUpdate(0), () -> sendColorUpdate(1)));
        frequencyField = addButton(new GuiTextField(this, 50, 103, 98, 11));
        frequencyField.setMaxStringLength(FrequencyManager.MAX_FREQ_LENGTH);
        frequencyField.setBackground(BackgroundType.INNER_SCREEN);
        frequencyField.setEnterHandler(this::setFrequency);
        frequencyField.setInputValidator(InputValidator.or(InputValidator.DIGIT, InputValidator.LETTER, InputValidator.FREQUENCY_CHARS));
        frequencyField.addCheckmarkButton(this::setFrequency);
        updateButtons();
    }

    public ITextComponent getSecurity(Frequency freq) {
        if (freq.isPublic()) {
            return MekanismLang.PUBLIC.translate();
        }
        return MekanismLang.PRIVATE.translateColored(EnumColor.DARK_RED);
    }

    private void updateButtons() {
        if (tile.getOwnerUUID() == null) {
            return;
        }
        List<String> text = new ArrayList<>();
        if (privateMode) {
            for (Frequency freq : tile.getPrivateCache(FrequencyType.TELEPORTER)) {
                text.add(freq.getName());
            }
        } else {
            for (Frequency freq : tile.getPublicCache(FrequencyType.TELEPORTER)) {
                text.add(MekanismLang.GENERIC_WITH_PARENTHESIS.translate(freq.getName(), freq.getClientOwner()).getString());
            }
        }
        scrollList.setText(text);
        if (privateMode) {
            publicButton.active = true;
            privateButton.active = false;
        } else {
            publicButton.active = false;
            privateButton.active = true;
        }
        if (scrollList.hasSelection()) {
            Frequency freq = privateMode ? tile.getPrivateCache(FrequencyType.TELEPORTER).get(scrollList.getSelection()) :
                             tile.getPublicCache(FrequencyType.TELEPORTER).get(scrollList.getSelection());
            TeleporterFrequency teleporterFrequency = tile.getFrequency(FrequencyType.TELEPORTER);
            setButton.active = teleporterFrequency == null || !teleporterFrequency.equals(freq);
            UUID ownerUUID = tile.getOwnerUUID();
            deleteButton.active = ownerUUID != null && freq.ownerMatches(ownerUUID);
        } else {
            setButton.active = false;
            deleteButton.active = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!init) {
            TeleporterFrequency frequency = getFrequency();
            if (frequency != null) {
                init = true;
                privateMode = frequency.isPrivate();
            }
        }
        //TODO: Why do we call updateButtons every tick?
        updateButtons();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        updateButtons();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void setFrequency() {
        setFrequency(frequencyField.getText());
        frequencyField.setText("");
        updateButtons();
    }

    @Override
    protected void drawForegroundText(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawString(matrix, OwnerDisplay.of(tile.getOwnerUUID(), tile.getOwnerName()).getTextComponent(), 8, imageHeight - 92, titleTextColor());
        ITextComponent frequencyComponent = MekanismLang.FREQUENCY.translate();
        drawString(matrix, frequencyComponent, 32, 81, titleTextColor());
        ITextComponent securityComponent = MekanismLang.SECURITY.translate("");
        drawString(matrix, securityComponent, 32, 91, titleTextColor());
        Frequency freq = tile.getFrequency(FrequencyType.TELEPORTER);
        int frequencyOffset = getStringWidth(frequencyComponent) + 1;
        if (freq == null) {
            drawString(matrix, MekanismLang.NONE.translateColored(EnumColor.DARK_RED), 32 + frequencyOffset, 81, subheadingTextColor());
            drawString(matrix, MekanismLang.NONE.translateColored(EnumColor.DARK_RED), 32 + getStringWidth(securityComponent), 91, subheadingTextColor());
        } else {
            drawTextScaledBound(matrix, freq.getName(), 32 + frequencyOffset, 81, subheadingTextColor(), imageWidth - 32 - frequencyOffset - 4);
            drawString(matrix, getSecurity(freq), 32 + getStringWidth(securityComponent), 91, subheadingTextColor());
        }
        drawTextScaledBound(matrix, MekanismLang.SET.translate(), 27, 104, titleTextColor(), 20);
        super.drawForegroundText(matrix, mouseX, mouseY);
    }

    public void setFrequency(String freq) {
        if (!freq.isEmpty()) {
            Mekanism.packetHandler.sendToServer(PacketGuiSetFrequency.create(FrequencyUpdate.SET_TILE, FrequencyType.TELEPORTER, new FrequencyIdentity(freq, !privateMode), tile.getBlockPos()));
        }
    }

    public TeleporterFrequency getFrequency() {
        return tile.getFrequency(FrequencyType.TELEPORTER);
    }

    private void sendColorUpdate(int extra) {
        TeleporterFrequency freq = getFrequency();
        if (freq != null) {
            Mekanism.packetHandler.sendToServer(PacketTeleporterSetColor.create(tile.getTilePos(), freq, extra));
        }
    }
}
package mekanism.client.gui.element.window.filter;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.api.text.EnumColor;
import mekanism.api.text.ILangEntry;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.button.MekanismImageButton;
import mekanism.client.gui.element.button.TranslationButton;
import mekanism.client.gui.element.slot.GuiSequencedSlotDisplay;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.client.gui.element.window.GuiWindow;
import mekanism.client.jei.interfaces.IJEIGhostTarget.IGhostIngredientConsumer;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.content.filter.IFilter;
import mekanism.common.content.transporter.SorterFilter;
import mekanism.common.inventory.container.SelectedWindowData;
import mekanism.common.network.to_server.PacketEditFilter;
import mekanism.common.network.to_server.PacketNewFilter;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ITileFilterHolder;
import mekanism.common.util.StackUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiFilter<FILTER extends IFilter<FILTER>, TILE extends TileEntityMekanism & ITileFilterHolder<? super FILTER>> extends GuiWindow
      implements GuiFilterHelper<TILE> {

    public static final Predicate<ItemStack> NOT_EMPTY = stack -> !stack.isEmpty();
    public static final Predicate<ItemStack> NOT_EMPTY_BLOCK = stack -> !stack.isEmpty() && stack.getItem() instanceof BlockItem;

    private final ITextComponent filterName;
    @Nullable
    protected final FILTER origFilter;
    protected final FILTER filter;
    protected final TILE tile;
    private final boolean isNew;

    protected ITextComponent status = MekanismLang.STATUS_OK.translateColored(EnumColor.DARK_GREEN);
    protected GuiSequencedSlotDisplay slotDisplay;
    private int ticker;

    protected GuiFilter(IGuiWrapper gui, int x, int y, int width, int height, ITextComponent filterName, TILE tile, @Nullable FILTER origFilter) {
        super(gui, x, y, width, height, SelectedWindowData.UNSPECIFIED);
        this.tile = tile;
        this.origFilter = origFilter;
        this.filterName = filterName;
        if (origFilter == null) {
            isNew = true;
            filter = createNewFilter();
        } else {
            isNew = false;
            filter = origFilter.clone();
        }
        init();
        if (!isFocusOverlay()) {
            if (isNew && getFilterSelect(gui, tile) != null) {
                //If it is a new filter and we have a filter select screen add a back button instead of a close button
                addChild(new MekanismImageButton(gui, this.x + 6, this.y + 6, 11, 14, getButtonLocation("back"), this::openFilterSelect));
            } else {
                super.addCloseButton();
            }
        }
        if (filter.hasFilter()) {
            slotDisplay.updateStackList();
        }
    }

    @Override
    protected void addCloseButton() {
        //No-op the super close button addition
    }

    protected int getSlotOffset() {
        return 18;
    }

    protected int getScreenHeight() {
        return 43;
    }

    protected int getScreenWidth() {
        return 116;
    }

    protected int getLeftButtonX() {
        return x + width / 2 - 61;
    }

    protected void init() {
        int screenTop = relativeY + 18;
        int screenBottom = screenTop + getScreenHeight();
        addChild(new GuiInnerScreen(gui(), relativeX + 29, screenTop, getScreenWidth(), getScreenHeight(), this::getScreenText).clearFormat());
        addChild(new TranslationButton(gui(), getLeftButtonX(), getGuiTop() + screenBottom + 2, 60, 20,
              isNew ? MekanismLang.BUTTON_CANCEL : MekanismLang.BUTTON_DELETE, () -> {
            if (origFilter != null) {
                Mekanism.packetHandler.sendToServer(new PacketEditFilter(tile.getBlockPos(), true, origFilter, null));
            }
            close();
        }));
        addChild(new TranslationButton(gui(), getLeftButtonX() + 62, getGuiTop() + screenBottom + 2, 60, 20, MekanismLang.BUTTON_SAVE, this::validateAndSave));
        addChild(new GuiSlot(SlotType.NORMAL, gui(), relativeX + 7, relativeY + getSlotOffset()).setRenderHover(true).setGhostHandler(getGhostHandler()));
        slotDisplay = addChild(new GuiSequencedSlotDisplay(gui(), relativeX + 8, relativeY + getSlotOffset() + 1, this::getRenderStacks));
    }

    @Nullable
    protected IGhostIngredientConsumer getGhostHandler() {
        return null;
    }

    private void openFilterSelect() {
        //Add the window for the filter select dialog to the parent gui
        gui().addWindow(getFilterSelect(gui(), tile));
        //And close the filter filter
        close();
    }

    protected List<ITextComponent> getScreenText() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(MekanismLang.STATUS.translate(status));
        return list;
    }

    protected void validateAndSave() {
        if (filter.hasFilter()) {
            saveFilter();
        } else {
            filterSaveFailed(getNoFilterSaveError());
        }
    }

    protected static <FILTER extends SorterFilter<FILTER>> void validateAndSaveSorterFilter(GuiFilter<FILTER, ?> guiFilter, GuiTextField minField, GuiTextField maxField) {
        //Note: This is here not in GuiSorterFilterHelper so that it can access the saveFilter/filterSaveFailed methods
        if (guiFilter.filter.hasFilter()) {
            if (minField.getText().isEmpty() || maxField.getText().isEmpty()) {
                guiFilter.filterSaveFailed(MekanismLang.SORTER_FILTER_SIZE_MISSING);
            } else {
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());
                if (max >= min && max <= 64) {
                    guiFilter.filter.min = min;
                    guiFilter.filter.max = max;
                    guiFilter.saveFilter();
                } else if (min > max) {
                    guiFilter.filterSaveFailed(MekanismLang.SORTER_FILTER_MAX_LESS_THAN_MIN);
                } else { //max > 64 || min > 64
                    guiFilter.filterSaveFailed(MekanismLang.SORTER_FILTER_OVER_SIZED);
                }
            }
        } else {
            guiFilter.filterSaveFailed(guiFilter.getNoFilterSaveError());
        }
    }

    protected void filterSaveFailed(ILangEntry reason, Object... args) {
        status = reason.translateColored(EnumColor.DARK_RED, args);
        ticker = 20;
    }

    protected void saveFilter() {
        if (isNew) {
            Mekanism.packetHandler.sendToServer(new PacketNewFilter(tile.getBlockPos(), filter));
        } else {
            Mekanism.packetHandler.sendToServer(new PacketEditFilter(tile.getBlockPos(), false, origFilter, filter));
        }
        close();
    }

    protected abstract ILangEntry getNoFilterSaveError();

    @Nonnull
    protected abstract List<ItemStack> getRenderStacks();

    @Override
    public void renderForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.renderForeground(matrix, mouseX, mouseY);
        drawTextScaledBound(matrix, (isNew ? MekanismLang.FILTER_NEW : MekanismLang.FILTER_EDIT).translate(filterName), relativeX + 30, relativeY + 6, titleTextColor(), 110);
    }

    @Override
    public void tick() {
        super.tick();
        if (ticker > 0) {
            ticker--;
        } else {
            status = MekanismLang.STATUS_OK.translateColored(EnumColor.DARK_GREEN);
        }
    }

    protected abstract FILTER createNewFilter();

    public static boolean mouseClickSlot(IGuiWrapper gui, int button, double mouseX, double mouseY, double xMin, double yMin, Predicate<ItemStack> stackValidator,
          Consumer<ItemStack> itemConsumer) {
        if (button == 0) {
            double xAxis = mouseX - gui.getLeft();
            double yAxis = mouseY - gui.getTop();
            if (xAxis >= xMin && xAxis < xMin + 16 && yAxis >= yMin && yAxis < yMin + 16) {
                ItemStack toSet;
                if (Screen.hasShiftDown()) {
                    toSet = ItemStack.EMPTY;
                } else {
                    ItemStack stack = minecraft.player.inventory.getCarried();
                    if (!stackValidator.test(stack)) {
                        return false;
                    }
                    toSet = StackUtils.size(stack, 1);
                }
                itemConsumer.accept(toSet);
                return true;
            }
        }
        return false;
    }
}

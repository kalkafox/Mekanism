package mekanism.common.content.filter;

import mekanism.api.math.MathUtils;

public enum FilterType {
    MINER_ITEMSTACK_FILTER,
    MINER_MATERIAL_FILTER,
    MINER_MODID_FILTER,
    MINER_TAG_FILTER,
    SORTER_ITEMSTACK_FILTER,
    SORTER_MATERIAL_FILTER,
    SORTER_MODID_FILTER,
    SORTER_TAG_FILTER,
    OREDICTIONIFICATOR_ITEM_FILTER,
    QIO_ITEMSTACK_FILTER,
    QIO_TAG_FILTER,
    //TODO - 1.17: Evaluate moving the QIO modid filter above the tag filter. We can't do that now as it uses the index for saving purposes
    QIO_MODID_FILTER;

    private static final FilterType[] FILTERS = values();

    public static FilterType byIndexStatic(int index) {
        return MathUtils.getByIndexMod(FILTERS, index);
    }
}
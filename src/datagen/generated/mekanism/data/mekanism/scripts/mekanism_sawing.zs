import mods.mekanism.api.ingredient.ItemStackIngredient;

/*
 * Adds five Sawing Recipes that do the following:
 * 1) Adds a recipe for sawing Melon Slices into Melon Seeds.
 * 2) Adds a recipe for sawing fifteen Leaves into a 5% chance of Sawdust.
 * 3) Adds a recipe for sawing five Saplings into a 75% chance of Sawdust.
 * 4) Adds a recipe for sawing a Crafting Table into five Oak Planks and a 25% chance of Sawdust.
 * 5) Adds a recipe for sawing Books into Paper and Leather.
*/

// <recipetype:mekanism:sawing>.addRecipe(name as string, input as ItemStackIngredient, mainOutput as IItemStack)
// <recipetype:mekanism:sawing>.addRecipe(name as string, input as ItemStackIngredient, secondaryOutput as MCWeightedItemStack)
// <recipetype:mekanism:sawing>.addRecipe(name as string, input as ItemStackIngredient, mainOutput as IItemStack, secondaryOutput as MCWeightedItemStack)
// <recipetype:mekanism:sawing>.addRecipe(name as string, input as ItemStackIngredient, secondaryOutput as IItemStack, secondaryChance as double)
// <recipetype:mekanism:sawing>.addRecipe(name as string, input as ItemStackIngredient, mainOutput as IItemStack, secondaryOutput as IItemStack, secondaryChance as double)

<recipetype:mekanism:sawing>.addRecipe("sawing/melon_to_seeds", ItemStackIngredient.from(<item:minecraft:melon_slice>), <item:minecraft:melon_seeds>);
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:sawing>.addRecipe("sawing/melon_to_seeds", <item:minecraft:melon_slice>, <item:minecraft:melon_seeds>);

<recipetype:mekanism:sawing>.addRecipe("sawing/leaves", ItemStackIngredient.from(<tag:items:minecraft:leaves>, 15), <item:mekanism:sawdust>.weight(0.5D));
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:sawing>.addRecipe("sawing/leaves", <tag:items:minecraft:leaves> * 15, <item:mekanism:sawdust>.weight(0.5D));

<recipetype:mekanism:sawing>.addRecipe("sawing/saplings", ItemStackIngredient.from(<tag:items:minecraft:saplings>), <item:mekanism:sawdust>, 0.75);
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:sawing>.addRecipe("sawing/saplings", <tag:items:minecraft:saplings>, <item:mekanism:sawdust>, 0.75);

<recipetype:mekanism:sawing>.addRecipe("sawing/workbench", ItemStackIngredient.from(<item:minecraft:crafting_table>), <item:minecraft:oak_planks> * 5, <item:mekanism:sawdust>.weight(0.25D));
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:sawing>.addRecipe("sawing/workbench", <item:minecraft:crafting_table>, <item:minecraft:oak_planks> * 5, <item:mekanism:sawdust>.weight(0.25D));

<recipetype:mekanism:sawing>.addRecipe("sawing/book", ItemStackIngredient.from(<item:minecraft:book>), <item:minecraft:paper> * 3, <item:minecraft:leather> * 6, 1.0);
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:sawing>.addRecipe("sawing/book", <item:minecraft:book>, <item:minecraft:paper> * 3, <item:minecraft:leather> * 6, 1.0);


//Removes the Sawing Recipe for producing Oak Planks from Oak Logs.

// <recipetype:mekanism:sawing>.removeByName(name as string)

<recipetype:mekanism:sawing>.removeByName("mekanism:sawing/log/oak");
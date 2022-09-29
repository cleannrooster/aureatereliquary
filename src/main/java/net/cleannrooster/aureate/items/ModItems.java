package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.aureate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {

    public static class aurium extends Aurium {
        public aurium(Item.Settings settings) {
            super(settings);
        }
    }


    public static final ArmorMaterial AURIUM_MATERIAL = new CustomArmorMaterial();
    public static final ToolMaterial AURIUM_TOOL_MATERIAL = new CustomToolMaterial();

    public static final Item AURIUM = new Aurium(new FabricItemSettings().group(aureate.GROUP));
    public static final Item AUREATE_HELMET = new AureateArmor(AURIUM_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(aureate.GROUP));
    public static final Item AUREATE_CHESTPLATE = new AureateArmor(AURIUM_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(aureate.GROUP));
    public static final Item AUREATE_LEGGINGS = new AureateArmor(AURIUM_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(aureate.GROUP));
    public static final Item AUREATE_BOOTS = new AureateArmor(AURIUM_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(aureate.GROUP));
    public static final Item AUREATE_SWORD = new AureateSword(AURIUM_TOOL_MATERIAL, 3,-2.4F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item QUANTUMBLADE = new Quantumblade(AURIUM_TOOL_MATERIAL, 3,-2.4F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item FLURRYBLADE = new FlurryBlade(AURIUM_TOOL_MATERIAL, 0,-1F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item SQUALLSWORD = new SquallSword(AURIUM_TOOL_MATERIAL, 5,-3F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item FIREBRAND = new Firebrand(AURIUM_TOOL_MATERIAL, 3,-2.4F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item MACUAHUITL = new Macuahuitl(AURIUM_TOOL_MATERIAL, 6,-3F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item GAUNTLET = new Gauntlet(new FabricItemSettings().maxCount(1).maxDamage(10));;
    public static final Item AUREATE_MACE = new AureateMace(AURIUM_TOOL_MATERIAL, 5,-3F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item PRIMAL_AXE = new PrimalAxe(AURIUM_TOOL_MATERIAL, 5,-3F,new FabricItemSettings().group(aureate.GROUP));;
    public static final Item SEETHING_FURY = new SeethingFury(AURIUM_TOOL_MATERIAL, 3,-2.4F,new FabricItemSettings().group(aureate.GROUP));;

    public static final Item GAUNTLETPROJ = new gauntletproj(new Item.Settings());;
    public static final Item AURIUMHORN = new AuriumHorn(new FabricItemSettings().maxCount(1).maxDamage(8).group(aureate.GROUP));;







    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier("aureate", "aurium"), AURIUM);
        Registry.register(Registry.ITEM, new Identifier("aureate", "relic_helmet"), AUREATE_HELMET);
        Registry.register(Registry.ITEM, new Identifier("aureate", "relic_chestplate"), AUREATE_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("aureate", "relic_leggings"), AUREATE_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("aureate", "relic_boots"), AUREATE_BOOTS);
        Registry.register(Registry.ITEM, new Identifier("aureate", "resonating_blade"), AUREATE_SWORD);
        Registry.register(Registry.ITEM, new Identifier("aureate", "aureate_mace"), AUREATE_MACE);
        Registry.register(Registry.ITEM, new Identifier("aureate", "quantumblade"), QUANTUMBLADE);
        Registry.register(Registry.ITEM, new Identifier("aureate", "flurryblade"), FLURRYBLADE);
        Registry.register(Registry.ITEM, new Identifier("aureate", "sword_of_squalls"), SQUALLSWORD);
        Registry.register(Registry.ITEM, new Identifier("aureate", "flame_sword"), FIREBRAND);
        Registry.register(Registry.ITEM, new Identifier("aureate", "aztec_sword"), MACUAHUITL);
        Registry.register(Registry.ITEM, new Identifier("aureate", "seething_blade"), SEETHING_FURY);

        Registry.register(Registry.ITEM, new Identifier("aureate", "gauntlet"), GAUNTLET);
        Registry.register(Registry.ITEM, new Identifier("aureate", "primal_axe"), PRIMAL_AXE);
        Registry.register(Registry.ITEM, new Identifier("aureate", "gauntletproj"), GAUNTLETPROJ);
        Registry.register(Registry.ITEM, new Identifier("aureate", "aureatehorn"), AURIUMHORN);

















    }
}
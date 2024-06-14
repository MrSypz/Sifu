package sypztep.sifu.client.render.item;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import sypztep.sifu.common.init.ModItems;

public class CustomRenderer {
    public CustomRenderer() {

    }
    public static void init() {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            Identifier id = Registries.ITEM.getId(item);

            CustomItemRenderer customItemRenderer = new CustomItemRenderer(id);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(customItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item, customItemRenderer);
            ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) -> {
                out.accept(new ModelIdentifier(id.getNamespace(), id.getPath() + "_gui", "inventory"));
                out.accept(new ModelIdentifier(id.getNamespace(), id.getPath() + "_handheld", "inventory"));
            }));
        }
    }
//    @Override
//    public void onInitializeModelLoader(Context pluginContext) {
//        for (Warfan item : ModItems.ALL_WARFAN) {
//            Identifier id = Registries.ITEM.getId(item);
//
//            CustomItemRenderer customItemRenderer = new CustomItemRenderer(id);
//            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(customItemRenderer);
//            BuiltinItemRendererRegistry.INSTANCE.register(item, customItemRenderer);
//
//            pluginContext.addModels(new ModelIdentifier(id.getNamespace(),id.getPath() + "_gui", "inventory"));
//            pluginContext.addModels(new ModelIdentifier(id.getNamespace(),id.getPath() + "_handheld", "inventory"));
//        }
//    }
}
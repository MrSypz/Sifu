package sypztep.sifu.client.render.item;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;

public class CustomRendererImlp implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context context) {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            String weaponName = Registries.ITEM.getId(item).getPath();

            CustomItemRenderer customItemRenderer = new CustomItemRenderer(weaponName);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(customItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), customItemRenderer);

            for (String getStyle : CustomItemRenderer.style) {
//                ModelIdentifier modelId = Sifu.id("item/" + weaponName + getStyle);
                context.addModels(ModelIdentifier.ofInventoryVariant(Sifu.id("item/" + weaponName + getStyle)).id());
//                System.out.println("debug" + ModelIdentifier.ofInventoryVariant(Sifu.id("item/" + weaponName + getStyle)).id());
            }
        }
    }
}
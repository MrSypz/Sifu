package sypztep.sifu.client.render.item;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import sypztep.sifu.common.init.ModItems;
@Deprecated
public class CustomRendererImlp implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context context) {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            Identifier id = Registries.ITEM.getId(item);

            CustomItemRenderer customItemRenderer = new CustomItemRenderer(id);
            System.out.println(customItemRenderer);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(customItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item, customItemRenderer);
            context.addModels(new ModelIdentifier(id, "_gui").id());
            context.addModels(new ModelIdentifier(id, "_handheld").id());
            System.out.println(id + "_handheld");
        }
    }
}
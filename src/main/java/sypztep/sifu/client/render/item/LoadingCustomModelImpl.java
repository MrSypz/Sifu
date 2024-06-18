package sypztep.sifu.client.render.item;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;
public class LoadingCustomModelImpl implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context context) {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            String weaponName = Registries.ITEM.getId(item).getPath();
            for (String getstyle : DynamicItemRenderer.style) {
                context.addModels(ModelIdentifier.ofInventoryVariant(Sifu.id("item/" + weaponName + getstyle)).id());
            }
        }
    }
}
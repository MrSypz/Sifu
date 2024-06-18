package sypztep.sifu.mixin.util;

import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;

import java.util.Set;

@Mixin(ItemRenderer.class) @Deprecated
public abstract class ItemRendererMixin {
    @Unique
    private static final Set<ModelTransformationMode> inventoryModes;

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel use3dModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            if (item == ModItems.WARDENRITE_WARFAN) {
                String name = Registries.ITEM.getId(item).getPath();

                if (inventoryModes.contains(renderMode)) {
//                    return value;
                return MinecraftClient.getInstance().getBakedModelManager().getModel(Sifu.id("item/" + name + "_gui"));
                } else {
                    return MinecraftClient.getInstance().getBakedModelManager().getModel(Sifu.id("item/" + name + "_handheld"));
                }
            }
            else return value;
        }
        return value;
    }

    static {
        inventoryModes = Set.of(ModelTransformationMode.GUI, ModelTransformationMode.GROUND);
    }
}

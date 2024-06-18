package sypztep.sifu.client.render.item;

import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Deprecated
public class CustomItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    public static List<String> style;
    private static final Set<ModelTransformationMode> inventoryModes;
    private final Identifier id;
    private final String weaponId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;
    private BakedModel worldModel;

    public CustomItemRenderer(String weaponName) {
        this.id = Sifu.id(weaponName + "_renderer");
        this.weaponId = weaponName;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();

        if (inventoryModes.contains(mode)) {
            System.out.println("Item Render: " + this.itemRenderer);
            System.out.println("Inv Model: " + this.inventoryModel);
            System.out.println("World Model: " + this.worldModel);
            this.itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
        } else {
            boolean leftHanded;
            switch (mode) {
                case FIRST_PERSON_LEFT_HAND:
                case THIRD_PERSON_LEFT_HAND:
                    leftHanded = true;
                    break;
                default:
                    leftHanded = false;
            }
            System.out.println("Item Render: " + this.itemRenderer);
            System.out.println("Inv Model: " + this.inventoryModel);
            System.out.println("World Model: " + this.worldModel);
            this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.worldModel);
        }
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return List.of(ResourceReloadListenerKeys.MODELS);
    }

    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.itemRenderer = client.getItemRenderer();
        this.inventoryModel = client.getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(Sifu.id("item/" + this.weaponId + "_gui")));
        this.worldModel = client.getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(Sifu.id("item/" + this.weaponId + "_handheld")));
    }

    static {
        inventoryModes = Set.of(ModelTransformationMode.GUI, ModelTransformationMode.GROUND);
        style = List.of("_gui", "_handheld");
    }
}

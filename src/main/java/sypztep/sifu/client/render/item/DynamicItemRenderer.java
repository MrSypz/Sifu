package sypztep.sifu.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import sypztep.sifu.Sifu;
import sypztep.sifu.common.init.ModItems;

import java.util.Collection;
import java.util.List;
import java.util.Set;
@Environment(EnvType.CLIENT)
public class DynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    public static List<String> style;
    private static final Set<ModelTransformationMode> inventoryModes;
    private final Identifier id;
    private final String weaponId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;
    private BakedModel worldModel;

    public DynamicItemRenderer(String weaponName) {
        this.id = Sifu.id(weaponName + "_renderer");
        this.weaponId = weaponName;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();

        if (inventoryModes.contains(mode)) {
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
        this.inventoryModel = client.getBakedModelManager().getModel(Sifu.id("item/" + this.weaponId + "_gui"));
        this.worldModel = client.getBakedModelManager().getModel(Sifu.id("item/" + this.weaponId + "_handheld"));
    }
    public static void init() {
        for (Item item : ModItems.ALL_CUSTOM3D) {
            String weaponName = Registries.ITEM.getId(item).getPath();

            DynamicItemRenderer customItemRenderer = new DynamicItemRenderer(weaponName);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(customItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), customItemRenderer);
        }
    }

    static {
        inventoryModes = Set.of(ModelTransformationMode.GUI, ModelTransformationMode.GROUND);
        style = List.of("_gui", "_handheld");
    }

}

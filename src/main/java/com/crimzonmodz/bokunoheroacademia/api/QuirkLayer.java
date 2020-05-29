package com.crimzonmodz.bokunoheroacademia.api;

import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.crimzonmodz.bokunoheroacademia.api.quirk.ModelQuirk;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

public class QuirkLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    private final ElytraModel<AbstractClientPlayerEntity> modelElytra = new ElytraModel<>();

    private final IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRenderer;

    public QuirkLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
        this.entityRenderer = entityRendererIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LazyOptional<IQuirk> lazyOptional = entitylivingbaseIn.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null || iquirk.getQuirks().isEmpty()) return;
        Quirk q = iquirk.getQuirks().get(0);
        if(q == null || !(q instanceof ModelQuirk)) return;
        ModelQuirk modelQuirk = (ModelQuirk) q;
        if(modelQuirk.getLayers(entityRenderer) == null) return;
        modelQuirk.getLayers(entityRenderer).forEach(layer -> {
            layer.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        });

        //this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        //IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelElytra.getRenderType(TEXTURE_ELYTRA), false, false);
        //this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}

package com.crimzonmodz.bokunoheroacademia.quirks.fly;

import com.crimzonmodz.bokunoheroacademia.BnHA;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

@OnlyIn(Dist.CLIENT)
public class WingsLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private final WingsModel modelWings = new WingsModel();
    public final ResourceLocation WINGS_TEXTURE = new ResourceLocation(BnHA.MODID, "textures/entity/wings.png");

    public WingsLayer(IEntityRenderer renderer) { super(renderer); }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        PlayerEntity playerEntity = entitylivingbaseIn;
        LazyOptional<IQuirk> lo = playerEntity.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iq = lo.orElse(null);
        boolean glint = false;
        if(iq != null && iq.getQuirk(0) != null) {
            glint = ( (QuirkFly) iq.getQuirk(0) ).isActive();
        }

        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.0D, 0.125D);
        this.getEntityModel().copyModelAttributesTo(this.modelWings);
        this.modelWings.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelWings.getRenderType(WINGS_TEXTURE), false, glint);
        this.modelWings.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
    }
}
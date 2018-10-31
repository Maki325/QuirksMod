package maki325.bnha.proxy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.api.skilltree.SkillRegistry;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.gui.hud.HudHandler;
import maki325.bnha.gui.skilltree.GuiST;
import maki325.bnha.net.playerjoin.MessageHandlerAddQuirk;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageHandlerChangePointsClient;
import maki325.bnha.net.quirk.MessageHandlerActivateClient;
import maki325.bnha.net.quirk.MessageHandlerChangeQuirkClient;
import maki325.bnha.net.quirk.hud.MessageChangeHudSkill;
import maki325.bnha.net.quirk.hud.MessageHandlerChangeHudSkillClient;
import maki325.bnha.net.quirk.messages.MessageActivateClient;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import maki325.bnha.net.tag.add.MessageAddTag;
import maki325.bnha.net.tag.add.MessageHandlerAddTagClient;
import maki325.bnha.net.tag.remove.MessageHandlerRemoveTagClient;
import maki325.bnha.net.tag.remove.MessageRemoveTag;
import maki325.bnha.util.KeyBindings;
import maki325.bnha.util.Reference;
import maki325.bnha.util.handlers.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		
		KeyBindings.init();
		
		CommonProxy.simpleNetworkWrapper.registerMessage(MessageHandlerActivateClient.class, MessageActivateClient.class, ACTIVATE_CLIENT_MESSAGE_ID, Side.CLIENT);
		
		CommonProxy.simpleNetworkWrapper.registerMessage(MessageHandlerAddQuirk.class, MessageAddQuirk.class, ADD_QUIRK_MESSAGE_ID, Side.CLIENT);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerChangeQuirkClient.class, MessageChangeQuirk.class, CHANGE_QUIRK_CLIENT_MESSAGE_ID, Side.CLIENT);

		//TAG
		simpleNetworkWrapper.registerMessage(MessageHandlerAddTagClient.class, MessageAddTag.class, CHANGE_ADD_TAG_CLIENT_MESSAGE_ID, Side.CLIENT);
		simpleNetworkWrapper.registerMessage(MessageHandlerRemoveTagClient.class, MessageRemoveTag.class, CHANGE_REMOVE_TAG_CLIENT_MESSAGE_ID, Side.CLIENT);

		//POINT SYSTEM
		simpleNetworkWrapper.registerMessage(MessageHandlerChangePointsClient.class, MessageChangePoints.class, POINTS_CLIENT_MESSAGE_ID, Side.CLIENT);

		//HUD SKILL SYSTEM
		simpleNetworkWrapper.registerMessage(MessageHandlerChangeHudSkillClient.class, MessageChangeHudSkill.class, HUD_SKILL_CLIENT_MESSAGE_ID, Side.CLIENT);
		
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	private static GuiHud hud;
	
	@Override
	public void postInit() {
		super.postInit();
        generateIconAtlas();
        generateSkillAtlas();
        
		hud = new GuiHud(Minecraft.getMinecraft());
		MinecraftForge.EVENT_BUS.register(new HudHandler(hud));
	}
	
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().player : super.getPlayer(ctx);
	}

    public void generateIconAtlas() {
        final int ICON_SIZE = 32;
        final int PER_ROW = 8;
        int index = 0;

        BufferedImage iconMissing = null;
		try {
			iconMissing = TextureUtil.readBufferedImage(ClientProxy.class.getResourceAsStream("/assets/" + Reference.MOD_ID + "/textures/quirks/none.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        BufferedImage atlas = new BufferedImage(ICON_SIZE * PER_ROW, ICON_SIZE * PER_ROW, BufferedImage.TYPE_INT_ARGB);
        Graphics g = atlas.createGraphics();

        g.drawImage(iconMissing, (index % PER_ROW) * ICON_SIZE, (index / PER_ROW) * ICON_SIZE, ICON_SIZE, ICON_SIZE, null);
        index++;
        
        for(Quirk q : QuirkRegistry.getQuirks()) {
            ResourceLocation identifier = q.getId();
            String path = "/assets/" + identifier.getResourceDomain() + "/textures/quirks/" + identifier.getResourcePath() + ".png";
            try {
                int iconU = (index % PER_ROW) * ICON_SIZE;
                int iconV = (index / PER_ROW) * ICON_SIZE;
                InputStream input = ClientProxy.class.getResourceAsStream(path);
                if(input != null)
                {
                    BufferedImage icon = TextureUtil.readBufferedImage(input);
                    if(icon.getWidth() != ICON_SIZE || icon.getHeight() != ICON_SIZE)
                    {
                    	BnHA.logger.error("Incorrect icon size for " + identifier.toString() + " (Must be 32 by 32 pixels)");
                        continue;
                    }
                    g.drawImage(icon, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    index++;
                } else {
                	BnHA.logger.error("Missing icon for " + identifier.toString());
                    g.drawImage(iconMissing, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    index++;
                }
                QuirkRegistry.setUV(q, iconU, iconV);
            }
            catch(Exception e) {
            	BnHA.logger.error("Unable to load icon for " + identifier.toString());
            }
        }
        
        g.dispose();
        Minecraft.getMinecraft().getTextureManager().loadTexture(GuiHud.QUIRK_TEXTURES, new DynamicTexture(atlas));
    }
    
    public void generateSkillAtlas() {
        final int ICON_SIZE = 16;
        final int PER_ROW = 16;
        int index = 0;

        BufferedImage iconMissing = null;
		try {
			iconMissing = TextureUtil.readBufferedImage(ClientProxy.class.getResourceAsStream("/assets/" + Reference.MOD_ID + "/textures/quirks/none.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        BufferedImage atlas = new BufferedImage(ICON_SIZE * PER_ROW, ICON_SIZE * PER_ROW, BufferedImage.TYPE_INT_ARGB);
        Graphics g = atlas.createGraphics();

        g.drawImage(iconMissing, (index % PER_ROW) * ICON_SIZE, (index / PER_ROW) * ICON_SIZE, ICON_SIZE, ICON_SIZE, null);
        index++;
        
        for(Skill s : SkillRegistry.getSkills()) {
            ResourceLocation identifier = s.getID();
            String path = "/assets/" + identifier.getResourceDomain() + "/textures/skills/" + identifier.getResourcePath() + ".png";
            try {
                int iconU = (index % PER_ROW) * ICON_SIZE;
                int iconV = (index / PER_ROW) * ICON_SIZE;
                InputStream input = ClientProxy.class.getResourceAsStream(path);
                if(input != null)
                {
                    BufferedImage icon = TextureUtil.readBufferedImage(input);
                    if(icon.getWidth() != ICON_SIZE || icon.getHeight() != ICON_SIZE)
                    {
                    	BnHA.logger.error("Incorrect icon size for " + identifier.toString() + " (Must be 16 by 16 pixels)");
                        continue;
                    }
                    g.drawImage(icon, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    index++;
                } else {
                	BnHA.logger.error("Missing icon for " + identifier.toString());
                    g.drawImage(iconMissing, iconU, iconV, ICON_SIZE, ICON_SIZE, null);
                    index++;
                }
                SkillRegistry.setUV(s, iconU, iconV);
            }
            catch(Exception e) {
            	BnHA.logger.error("Unable to load icon for " + identifier.toString());
            }
        }
        
        g.dispose();
        Minecraft.getMinecraft().getTextureManager().loadTexture(GuiST.SKILL_TEXTURES, new DynamicTexture(atlas));
    }
	
}


package com.TBK.WitchArchive.server;


import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CVNWitchArchiveCuriosities.MODID)

public class Events {



    @SubscribeEvent
    public static void onUseItem(PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().is(Items.STICK)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.x+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.x-=0.5D;
            }
                CVNWitchArchiveCuriosities.LOGGER.debug("X :" + CVNWitchArchiveCuriosities.x);
        }

        if(event.getItemStack().is(Items.BLAZE_ROD)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.y+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.y-=0.5D;
            }
            CVNWitchArchiveCuriosities.LOGGER.debug("Y :" + CVNWitchArchiveCuriosities.y);
        }
        if(event.getItemStack().is(Items.PRISMARINE_SHARD)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.z+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.z-=0.5D;
            }
            CVNWitchArchiveCuriosities.LOGGER.debug("Z :" + CVNWitchArchiveCuriosities.z);
        }

        if(event.getItemStack().is(Items.HEART_OF_THE_SEA)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.xq+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.xq-=0.5D;
            }
            CVNWitchArchiveCuriosities.LOGGER.debug("XQ :" + CVNWitchArchiveCuriosities.xq);
        }
        if(event.getItemStack().is(Items.FLINT)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.yq+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.yq-=0.5D;
            }
            CVNWitchArchiveCuriosities.LOGGER.debug("YQ :" + CVNWitchArchiveCuriosities.yq);
        }

        if(event.getItemStack().is(Items.AMETHYST_SHARD)){
            if(event.getEntity().isShiftKeyDown()){
                CVNWitchArchiveCuriosities.zq+=0.5D;
            }else {
                CVNWitchArchiveCuriosities.zq-=0.5D;
            }
            CVNWitchArchiveCuriosities.LOGGER.debug("ZQ :" + CVNWitchArchiveCuriosities.zq);
        }
        CVNWitchArchiveCuriosities.LOGGER.debug("X :" + CVNWitchArchiveCuriosities.x + " Y :"+CVNWitchArchiveCuriosities.y+
                " Z :" + CVNWitchArchiveCuriosities.z + " XQ :"+CVNWitchArchiveCuriosities.xq+
                " YQ :" + CVNWitchArchiveCuriosities.yq + " ZQ :"+CVNWitchArchiveCuriosities.zq);

    }

}

package cz.rychleji.blockprot.bukkit.utils;

import de.sean.blockprot.bukkit.config.DefaultConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TweakUtils {
    protected DefaultConfig config;
    public TweakUtils(DefaultConfig config){
        this.config = config;
    }

    public boolean IsBlockWithInventory(Block block){
        return IsBlockWithInventory(block.getType());
    }

    public boolean IsBlockWithInventory(Material material){
        //check config to verify if the block is lockable_tile_entities
        return this.isInConfigAsTile(material);
    }

    private boolean isInConfigAsTile(Material material){
        return config.isLockableTileEntity(material);
    }
}

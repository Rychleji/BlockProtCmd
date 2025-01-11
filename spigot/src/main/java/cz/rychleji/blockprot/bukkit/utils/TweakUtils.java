/*
 * Copyright (C) 2021 - 2024 spnda
 * This file is part of BlockProt <https://github.com/spnda/BlockProt>.
 *
 * BlockProt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockProt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockProt.  If not, see <http://www.gnu.org/licenses/>.
 */

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

package cz.rychleji.blockprot.bukkit.listeners;

import cz.rychleji.blockprot.bukkit.BlockProtCmd;
import cz.rychleji.blockprot.bukkit.utils.TweakUtils;
import de.sean.blockprot.bukkit.BlockProt;
import de.sean.blockprot.bukkit.Permissions;
import de.sean.blockprot.bukkit.Translator;
import de.sean.blockprot.bukkit.config.DefaultConfig;
import de.sean.blockprot.bukkit.events.BlockLockOnPlaceEvent;
import de.sean.blockprot.bukkit.integrations.PluginIntegration;
import de.sean.blockprot.bukkit.listeners.BlockEventListener;
import de.sean.blockprot.bukkit.nbt.BlockNBTHandler;
import de.sean.blockprot.bukkit.nbt.PlayerSettingsHandler;
import de.sean.blockprot.bukkit.nbt.StatHandler;
import de.sean.blockprot.bukkit.util.BlockUtil;
import de.sean.blockprot.nbt.LockReturnValue;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BlockEventListenerTweaked extends BlockEventListener {
    /**
     * @param blockProt
     */
    public BlockEventListenerTweaked(@NotNull BlockProtCmd blockProt) {
        super(blockProt);
    }

    /**
     * @param event
     */
    @Override
    public void onBlockBurn(BlockBurnEvent event) {
        super.onBlockBurn(event);
    }

    /**
     * @param event
     */
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        super.onBlockBreak(event);
    }

    /**
     * We need to catch shulker box breaks separately with the lowest priority possible,
     * as otherwise other plugins might have cancelled it and a player could dupe the box.
     *
     * @param event
     */
    @Override
    public void onShulkerBoxBreak(BlockBreakEvent event) {
        super.onShulkerBoxBreak(event);
    }

    /**
     * @param event
     */
    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if (BlockProt.getDefaultConfig().isWorldExcluded(event.getBlock().getWorld())) return;
        if (!event.getPlayer().hasPermission(Permissions.LOCK.key())) return;
        if (!BlockProt.getDefaultConfig().isLockable(event.getBlock().getType())) return;

        Block block = event.getBlockPlaced();
        String playerUuid = event.getPlayer().getUniqueId().toString();
        BlockNBTHandler handler = new BlockNBTHandler(block);

        // We only try to lock the block if it isn't locked already.
        // Shulker boxes might already be locked, from previous placing.
        if (handler.isNotProtected()) {
            TweakUtils utils = ((BlockProtCmd)blockProt).getUtils();
            if(utils.IsBlockWithInventory(block)) return; //block is not a chest or any other tile from config

            PlayerSettingsHandler settingsHandler = new PlayerSettingsHandler(event.getPlayer());

            // Lock the block instantly if the setting is enabled.
            if (settingsHandler.getLockOnPlace()) {
                BlockLockOnPlaceEvent lockOnPlaceEvent = new BlockLockOnPlaceEvent(event.getBlock(), event.getPlayer());

                Bukkit.getPluginManager().callEvent(lockOnPlaceEvent);
                if (!lockOnPlaceEvent.isCancelled()) {
                    LockReturnValue lock = handler.lockBlock(event.getPlayer());
                    if (!lock.success) {
                        event.setCancelled(true);
                        if (lock.reason != null) {
                            event.getPlayer().spigot().sendMessage(
                                ChatMessageType.ACTION_BAR,
                                TextComponent.fromLegacyText(Translator.get(lock.reason)));
                        }
                        return;
                    }

                    settingsHandler.getFriendsStream()
                        .filter(fh -> PluginIntegration.filterFriendByUuidForAll(UUID.fromString(fh.getName()), event.getPlayer(), block))
                        .forEach(handler::addFriend);
                }

                if (BlockProt.getDefaultConfig().disallowRedstoneOnPlace()) {
                    handler.getRedstoneHandler().setAll(false);
                }
            }

            Bukkit.getScheduler().runTaskLater(
                this.blockProt,
                () -> {
                    if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                        // We cannot use BlockNBTHandler#applyToOtherContainer, because we want the
                        // data to be copied to this new chest, instead of the old chest being effectively
                        // cleared.
                        final BlockState doubleChestState = BlockUtil.getDoubleChest(block);
                        if (doubleChestState != null) {
                            final BlockNBTHandler doubleChestHandler = new BlockNBTHandler(doubleChestState.getBlock());
                            if (doubleChestHandler.isNotProtected() || doubleChestHandler.isOwner(playerUuid)) {
                                handler.mergeHandler(doubleChestHandler);
                            } else {
                                // We can't cancel the event 1 tick later, its already executed. We'll just need to destroy the block and drop it.
                                event.getPlayer().getWorld().getBlockAt(block.getLocation()).breakNaturally();
                            }

                            // Remove the container as we break it, but also remove it when successful to remove duplicates.
                            StatHandler.removeContainer(event.getPlayer(), block);
                        }
                    } else {
                        handler.setName(BlockUtil.getHumanReadableBlockName(block.getType()));
                        handler.applyToOtherContainer();
                    }
                },
                1
            );
        }
    }

    /**
     * @param event
     */
    @Override
    public void onBlockPhysics(@NotNull BlockPhysicsEvent event) {
        super.onBlockPhysics(event);
    }

    /**
     * @param event
     */
    @Override
    public void onSignChanged(@NotNull SignChangeEvent event) {
        super.onSignChanged(event);
    }
}

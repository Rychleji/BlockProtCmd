package cz.rychleji.blockprot.bukkit;

import cz.rychleji.blockprot.bukkit.utils.TweakUtils;
import de.sean.blockprot.bukkit.BlockProt;
import de.sean.blockprot.bukkit.integrations.PluginIntegration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class BlockProtCmd extends BlockProt {
    protected TweakUtils tUtils = null;

    public TweakUtils getUtils(){return tUtils;}

    /**
     * Gets a unmodifiable list of all registered {@link PluginIntegration}s.
     *
     * @return List of all registered integrations.
     * @since 0.4.0
     */
    @Override
    public List<PluginIntegration> getIntegrations() {
        return super.getIntegrations();
    }

    /**
     *
     */
    @Override
    public void onLoad() {
        super.onLoad();
    }

    /**
     *
     */
    @Override
    public void onEnable() {
        super.onEnable();
    }

    /**
     *
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Reloads the config and the translation files (possibly changed through config).
     *
     * @since 1.0.0
     */
    @Override
    public void reloadConfigAndTranslations() {
        super.reloadConfigAndTranslations();
        this.tUtils = new TweakUtils(BlockProt.defaultConfig);
    }

    /**
     * Get a plugin by string ID from Bukkit's {@link PluginManager}.
     *
     * @param pluginName The ID of the plugin to get.
     * @return The main plugin instance or null if not found.
     * @since 0.4.0
     */
    @Override
    public @Nullable Plugin getPlugin(String pluginName) {
        return super.getPlugin(pluginName);
    }

    /**
     * Saves a config file and reads it. Relative to {@link JavaPlugin#getDataFolder()}.
     *
     * @param folder  The name of the folder where the file is located.
     * @param name    The name of the resource.
     * @param replace Whether or not to replace the file if it already exists.
     * @return The YamlConfiguration.
     * @since 0.4.7
     */
    @Override
    public @NotNull YamlConfiguration saveAndLoadConfigFile(String folder, String name, boolean replace) {
        return super.saveAndLoadConfigFile(folder, name, replace);
    }

    /**
     * @return
     */
    @Override
    protected @NotNull File getFile() {
        return super.getFile();
    }

    /**
     * @return
     */
    @Override
    public @NotNull FileConfiguration getConfig() {
        return super.getConfig();
    }

    /**
     *
     */
    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }

    /**
     *
     */
    @Override
    public void saveConfig() {
        super.saveConfig();
    }

    /**
     *
     */
    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
    }

    /**
     * @param resourcePath
     * @param replace
     */
    @Override
    public void saveResource(@NotNull String resourcePath, boolean replace) {
        super.saveResource(resourcePath, replace);
    }

    /**
     * @param filename
     * @return
     */
    @Override
    public @Nullable InputStream getResource(@NotNull String filename) {
        return super.getResource(filename);
    }

    /**
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.onCommand(sender, command, label, args);
    }

    /**
     * @param sender
     * @param command
     * @param alias
     * @param args
     * @return
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return super.onTabComplete(sender, command, alias, args);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public @Nullable PluginCommand getCommand(@NotNull String name) {
        return super.getCommand(name);
    }

    /**
     * @param worldName
     * @param id
     * @return
     */
    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

    /**
     * @param worldName
     * @param id
     * @return
     */
    @Override
    public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
        return super.getDefaultBiomeProvider(worldName, id);
    }

    /**
     * @return
     */
    @Override
    public @NotNull Logger getLogger() {
        return super.getLogger();
    }

    /**
     * @return
     */
    @Override
    public @NotNull String toString() {
        return super.toString();
    }
}

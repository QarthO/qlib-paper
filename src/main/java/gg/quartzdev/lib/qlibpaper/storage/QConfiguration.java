package gg.quartzdev.lib.qlibpaper.storage;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public abstract class QConfiguration
{
    private final JavaPlugin plugin;
    private final String fileName;
    private final String filePath;
    private final boolean useSchema;
    private double schemaVersion = 1.0;
    private final double minSupportedSchema = 1.0;
    private final File file;
    protected YamlConfiguration yamlConfiguration;
    protected Set<ConfigOption<?>> options;
    public HashMap<String, ConfigOption<?>> configOptions;

    public QConfiguration(JavaPlugin plugin, String fileName, boolean useSchema)
    {
        this.plugin = plugin;
        this.fileName = fileName;
        String fileSeparator = FileSystems.getDefault().getSeparator();
        filePath =
                plugin.getDataFolder().getPath() +
                        fileSeparator +
                        fileName.replaceAll("/", fileSeparator);
        file = new File(filePath);
        setupDirectory(file.getParentFile());
        configOptions = new HashMap<>();
        this.useSchema = useSchema;
        loadFile();
    }

    /**
     * Creates the plugin data folder if it doesn't exist
     *
     * @param directory the {@link File} (directory) to set up
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setupDirectory(File directory)
    {
        try
        {
            directory.mkdirs();
        } catch (SecurityException exception)
        {
            QLogger.error(GenericMessages.ERROR_FILE_CREATE.parse("file", directory.getPath() + " Directory"));
        }
    }

    /**
     * This will create the file if it doesn't exist
     * Then load the file into memory
     * If the file is using a schema (config-version), it will validate the schema
     * Then it will stamp the file with the current version of the plugin (not schema)
     */
    private void loadFile()
    {
        try
        {
            if (file.createNewFile())
            {
                plugin.saveResource(fileName, true);
                QLogger.info(GenericMessages.FILE_CREATE.parse("file", fileName));
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            if (!useSchema)
            {
                return;
            }
            if (!validateSchema())
            {
                QLogger.info("Unsupported Config Schema... Reset your config");
            }
            stampFile();
        } catch (IOException exception)
        {
            QLogger.error(GenericMessages.ERROR_FILE_CREATE.parse("file", fileName));
            QLogger.error(exception.getMessage());
        }
    }

    /**
     * Updates the comments on the schema ('config-version') to show the last version of the plugin the config was loaded with
     */
    @SuppressWarnings("UnstableApiUsage")
    public void stampFile()
    {
        List<String> notes = yamlConfiguration.getComments("config-version");
        if (!notes.isEmpty())
        {
            notes.remove(notes.size() - 1);
        }
        notes.add("Last loaded with " + plugin.getName() + " v" + plugin.getPluginMeta().getVersion());
        yamlConfiguration.setComments("config-version", notes);
        save();
    }

    /**
     * Validates the schema version of the config file
     * Note: Currently not implemented, but planned to auto-update the config to the latest schema version
     *
     * @return true if the schema is supported, false otherwise
     */
    public boolean validateSchema()
    {
        if (!yamlConfiguration.contains("config-version"))
        {
            yamlConfiguration.set("config-version", schemaVersion);
        }
        loadSchemaVersion();
        return schemaVersion >= minSupportedSchema;
    }

    /**
     * Saves the config file
     */
    public void save()
    {
        saveAllData();
        try
        {
            yamlConfiguration.save(file);
        } catch (IOException exception)
        {
            QLogger.error(GenericMessages.ERROR_FILE_SAVE.parse(QPlaceholder.FILE, filePath));
        }
    }

    /**
     * Reloads the config file
     */
    public void reload()
    {
        loadFile();
        loadAllData();
    }

    /**
     * Loads all data from the config file. To be implemented by subclasses
     */
    public abstract void loadAllData();

    /**
     * Saves all data to the config file. To be implemented by subclasses
     * It's recommended to save individual data as you update it, rather than saving it all at once
     */
    public abstract void saveAllData();


    /**
     * Reads the schema ('config-version') from the config file
     */
    public void loadSchemaVersion()
    {
        this.schemaVersion = getNumber("config-version").doubleValue();
    }

    /**
     * Gets the schema ('config-version') from the config file
     *
     * @return the schema
     */
    public double getSchema()
    {
        return this.schemaVersion;
    }

    /**
     * Parses string
     *
     * @param path - location in the config
     * @return - the {@link Number} that is represented by the string value found at the given path. Will will return a {@link Number} of value 0 if unable to parse the string.
     */
    public @NotNull Number getNumber(String path)
    {
        Object data = yamlConfiguration.get(path);

//       If data isn't found
        if (data == null)
        {
            return 0;
        }
//        Convert to string and try parsing
        String rawData = data.toString();
        Number number;
        try
        {
            number = Double.parseDouble(rawData);
        } catch (NumberFormatException e1)
        {
            try
            {
                number = Integer.parseInt(rawData);
            } catch (NumberFormatException e2)
            {
                try
                {
                    number = Long.parseLong(rawData);
                } catch (NumberFormatException e3)
                {
                    return 0;
                }
            }
        }
        return number;
    }

    public @Nullable EntityType getEntityType(String path)
    {
        String entityTypeName = yamlConfiguration.getString(path);
        return getEntityTypeFromName(entityTypeName);
    }

    private @Nullable EntityType getEntityTypeFromName(String name)
    {
        if (name != null)
            try
            {
                return EntityType.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ignored)
            {
            }
        return null;
    }

    /**
     * Gets a {@link List} of {@link World}s by world name at the given path in the configuration file.
     * Will always return a list. If no worlds are found, an empty list will be returned.
     *
     * @param path the path in the configuration file
     * @return {@link List} of {@link World}s
     */
    public @NotNull List<World> getWorldList(String path)
    {
        List<World> worlds = new ArrayList<>();
        for (String worldName : yamlConfiguration.getStringList(path))
        {
            World world = Bukkit.getWorld(worldName);
            if (world != null)
            {
                worlds.add(world);
            }
        }
        return worlds;
    }

    /**
     * Gets a map of the base section containing all keys and values. Does the same as #getValues on {@link ConfigurationSection}
     *
     * @param deep Whether to get a deep list, as opposed to a shallow list.
     * @return Map of keys and values of this section.
     */
    public Map<String, Object> getValues(boolean deep)
    {
        return yamlConfiguration.getValues(deep);
    }


//    MATERIALS

    /**
     * Used to get a {@link Material} from a {@link String}
     *
     * @param name name of a material, case-insensitive
     * @return {@link Material} if found, null otherwise
     */
    private @Nullable Material getMaterialFromName(String name)
    {
        if (name != null)
            try
            {
                return Material.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ignored)
            {
            }
        return null;
    }

    /**
     * Gets a {@link Material} at the given path in the configuration file.
     *
     * @param path the path in the configuration file
     * @return {@link Material} if found, null otherwise
     */
    public @Nullable Material getMaterial(String path)
    {
        return getMaterialFromName(yamlConfiguration.getString(path));
    }

    /**
     * Gets a {@link List} of {@link Material}s at the given path in the configuration file.
     *
     * @param path the path in the configuration file
     * @return {@link List} of {@link Material}s if found, null otherwise
     */
    public @Nullable List<Material> getMaterialList(String path)
    {
        List<Material> materials = new ArrayList<>();
        for (String materialName : yamlConfiguration.getStringList(path))
        {
            final Material material = getMaterialFromName(materialName);
            if (material != null)
            {
                materials.add(material);
            }
        }
        return materials;
    }
}

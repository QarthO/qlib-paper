package gg.quartzdev.lib.qlibpaper.storage;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.lib.qlibpaper.QLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class QConfiguration {
    private final JavaPlugin plugin;
    private final String fileName;
    private final String filePath;
    private final boolean useSchema;
    private double schemaVersion = 1.0;
    private final double minSupportedScema = 1.0;
    private File file;
    protected YamlConfiguration yamlConfiguration;
    protected Set<ConfigOption<?>> options;
    public HashMap<String, ConfigOption<?>> configOptions;

    public QConfiguration(JavaPlugin plugin, String fileName, boolean useSchema){
        this.plugin = plugin;
        this.fileName = fileName;
        String fileSeparator = System.getProperty("file.separator");
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

    private void setupDirectory(File directory){
        try{
            if(directory.mkdirs()){
//                QLogger.info(GenericMessages.FILE_CREATE.parse(QPlaceholder.FILE, directory.getPath() + " Directory"));
            }
        } catch(SecurityException exception){
            QLogger.error(GenericMessages.ERROR_FILE_CREATE.parse("file", directory.getPath() + " Directory"));
        }
    }

    private void loadFile() {
        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
                QLogger.info(GenericMessages.FILE_CREATE.parse("file", fileName));
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            if(!useSchema){
                return;
            }
            if(!validateSchema()){
                QLogger.info("Unsupported Config Schema... Reset your config");
            }
            stampFile();
        } catch (IOException exception) {
            QLogger.error(GenericMessages.ERROR_FILE_CREATE.parse("file", fileName));
            QLogger.error(exception.getMessage());
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public void stampFile(){
        List<String> notes = new ArrayList<>();
        notes.add("Last loaded with " + plugin.getName() + " v" + plugin.getPluginMeta().getVersion());
//        notes.add("Last loaded with " + QPlugin.getName() + " v" + QPlugin.getVersion());
        yamlConfiguration.setComments("schema-version", notes);
        save();
    }

    public boolean validateSchema(){
        if(!yamlConfiguration.contains("schema-version")) {
            yamlConfiguration.set("schema-version", schemaVersion);
        }
        loadSchemaVersion();
        return schemaVersion >= minSupportedScema;
    }

    public void save(){
        saveAllData();
        try {
            yamlConfiguration.save(file);
        } catch(IOException exception){
            QLogger.error(GenericMessages.ERROR_FILE_SAVE.parse(QPlaceholder.FILE, filePath));
        }
    }

    public void reload(){
        loadFile();
        loadAllData();
    }

    public abstract void loadAllData();
    public abstract void saveAllData();

    public void loadSchemaVersion(){
        this.schemaVersion = getNumber("schema-version").doubleValue();
    }

    public double getSchema(){
        return this.schemaVersion;
    }

    protected @Nullable Object get(String path){
        return yamlConfiguration.get(path);
    }

    /**
     * Parses string
     * @param path - location in the config
     * @return - the {@link Number} that is represented by the string value found at the given path. Will will return a {@link Number} of value 0 if unable to parse the string.
     */
    public @NotNull Number getNumber(String path){
        Object data = yamlConfiguration.get(path);

//       If data isn't found
        if(data == null){
            return 0;
        }
//        Convert to string and try parsing
        String rawData = data.toString();
        Number number = null;
        try {
            number = Double.parseDouble(rawData);
        } catch(NumberFormatException e1) {
            try {
                number = Integer.parseInt(rawData);
            } catch(NumberFormatException e2) {
                try {
                    number = Long.parseLong(rawData);
                } catch(NumberFormatException e3) {
                    return 0;
                }
            }
        }
        return number;
    }

    /**
     * Gets a ${@link String } at the given path in the configuration file.
     * @param path - the path in the configuration
     * @return - the string value found at the given path. Returns an empty string if no data found
     */
    public @NotNull String getString(String path){

        Object data = yamlConfiguration.get(path);

//       If data isn't found
        if(data == null){
            return "";
        }

        return data.toString();

    }

    public @Nullable EntityType getEntityType(String path){
        Object data = yamlConfiguration.get(path);

//       If data isn't found
        if(data == null){
            return null;
        }

//        Convert to string and try parsing
        String rawData = data.toString();

        try {
            return EntityType.valueOf(rawData);
        } catch(IllegalArgumentException e){
            return null;
        }
    }

    public @Nullable Location getLocation(String path){
        return yamlConfiguration.getLocation(path);
    }

    public List<?> getList(String path){
        List<?> list = new ArrayList<>();
        return yamlConfiguration.getList(path, list);
    }

    public List<String> getStringList(String path){
        if(!yamlConfiguration.contains(path)){
            return new ArrayList<>();
        }
        return yamlConfiguration.getStringList(path);
    }

    public List<World> getWorldList(String path){
        List<World> worlds = new ArrayList<>();
        for(String worldName : getStringList(path)){
            World world = Bukkit.getWorld(worldName);
            if(world == null){
//                    logger.error(Language.ERROR_WORLD_NOT_FOUND.parse("world", worldName));
            }
            else {
                worlds.add(world);
            }
        }
        return worlds;
    }

    /**
     * Gets a map of the base section containing all keys and values. Does the same as #getValues on {@link ConfigurationSection}
     * @param deep Whether to get a deep list, as opposed to a shallow list.
     * @return Map of keys and values of this section.
     */
    public Map<String, Object> getValues(boolean deep){
        return yamlConfiguration.getValues(deep);
    }

    public @Nullable Material getMaterial(String path){
        Object data = yamlConfiguration.get(path);
        if(data == null){
            return null;
        }

        String rawData = data.toString();
        try {
            return Material.valueOf(rawData);
        } catch(IllegalArgumentException e){
            return null;
        }
    }

    public @Nullable List<Material> getMaterialList(String path){
        List<Material> materials = new ArrayList<>();
        for(String materialName : getStringList(path)){
            try {
                materials.add(Material.valueOf(materialName));
            } catch(IllegalArgumentException e){
                continue;
            }
        }
        return materials;
    }
}

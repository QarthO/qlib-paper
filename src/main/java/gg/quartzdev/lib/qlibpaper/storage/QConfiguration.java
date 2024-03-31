package gg.quartzdev.lib.qlibpaper.storage;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.lib.qlibpaper.QLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class QConfiguration {
    private final JavaPlugin plugin;
    private final String fileName;
    private final String filePath;
    private double schemaVersion = 1.0;
    private final double minSupportedScema = 1.0;
    private File file;
    protected YamlConfiguration yamlConfiguration;

    public QConfiguration(JavaPlugin plugin, String fileName){
        this.plugin = plugin;
        this.fileName = fileName;
        setupDataFolder(plugin.getDataFolder());
        String fileSeparator = System.getProperty("file.separator");
        filePath =
                plugin.getDataFolder().getPath() +
                fileSeparator +
                fileName.replaceAll("/", fileSeparator);
        loadFile();
    }


    private void setupDataFolder(File dataFolder){
        try{
            if(dataFolder.mkdirs()){
                QLogger.info(GenericMessages.FILE_CREATED.parse(QPlaceholder.FILE, dataFolder.getPath() + " Directory"));
            }
        } catch(SecurityException exception){
            QLogger.error(GenericMessages.ERROR_CREATE_FILE.parse("file", dataFolder.getPath() + " Directory"));
        }
    }

    private void loadFile() {
        file = new File(filePath);
        try {
            if (file.createNewFile()) {
                plugin.saveResource(fileName, true);
                QLogger.info(GenericMessages.FILE_CREATED.parse("file", fileName));
            }

            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            if(!validateSchema()){
                QLogger.info("Unsupported Config Schema... Reset your config");
            }
            stampFile();
        } catch (IOException exception) {
            QLogger.error(GenericMessages.ERROR_CREATE_FILE.parse("file", fileName));
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
        QLogger.info("schema: " + schemaVersion);
        return schemaVersion >= minSupportedScema;
    }

    public void save(){
        saveAllData();
        try {
            yamlConfiguration.save(file);
        } catch(IOException exception){
            QLogger.error(GenericMessages.ERROR_SAVE_FILE.parse(QPlaceholder.FILE, filePath));
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

    public @Nullable Object get(String path){
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
}

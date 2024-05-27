package gg.quartzdev.lib.qlibpaper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UpdateChecker {
    final private String slug;
    final private String loader;

    /**
     * Creates a new update checker
     * @param slug The modrinth slug
     * @param loader The loader
     */
    public UpdateChecker(String slug, String loader){
        this.slug = slug;
        this.loader = loader;
    }

    private String modrinthApiURL(){
        return "https://api.modrinth.com/v2/project/" + slug + "/version" +
                "?game_versions=[" + URLEncoder.encode("\"" + Bukkit.getMinecraftVersion() + "\"", StandardCharsets.UTF_8) + "]" +
                "&loaders=[" + URLEncoder.encode("\"" + loader + "\"", StandardCharsets.UTF_8) + "]";
    }

    private String modrinthDownloadURL(String versionString){
        return "https://modrinth.com/plugin/" + slug + "/version/" + versionString;
    }

    private JsonArray getSupportedVersions(){
        String url = modrinthApiURL();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException("Invalid response code: " + connection.getResponseCode());
            }
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            String jsonAsString = scanner.nextLine();
            JsonElement json = JsonParser.parseString(jsonAsString);
            return json.getAsJsonArray();
        } catch (final Exception e) {
            QLogger.error(GenericMessages.ERROR_UPDATE_CHECK.get());
            QLogger.error(e.getMessage());
            return null;
        }
    }

    /**
     *  Checks for updates asynchronously
     * @param plugin the instance of the plugin that will be used to run the async task
     * @param currentVersion the current version of the plugin
     * @param toNotify the {@link Player} to notify the results. The results will always be logged even if this is null
     */
    public void checkForUpdatesAsync(JavaPlugin plugin, String currentVersion, @Nullable Player toNotify){
        Bukkit.getAsyncScheduler().runDelayed(plugin, scheduledTask -> checkForUpdates(currentVersion, toNotify), 1, TimeUnit.SECONDS);
    }

    public void checkForUpdates(String currentVersion, @Nullable Player toNotify){
        QLogger.info(GenericMessages.UPDATE_CHECKING.get());
        JsonArray versions = getSupportedVersions();
        if(versions == null || versions.isEmpty()){
            return;
        }
        for(JsonElement version : versions){
            String versionString = version.getAsJsonObject().get("version_number").getAsString();
            if(compareVersionStrings(currentVersion, versionString) == -1){
                Audience audience = toNotify == null ? Bukkit.getConsoleSender() : Audience.audience(toNotify, Bukkit.getConsoleSender());
                Sender.message(audience, GenericMessages.UPDATE_AVAILABLE
                        .parse("version", versionString));
                Sender.message(audience,GenericMessages.UPDATE_DOWNLOAD_URL
                        .parse("download_url", modrinthDownloadURL(versionString)));
                return;
            }
        }
        QLogger.info(GenericMessages.UPDATE_NOT_AVAILABLE.get());
    }

    /**
     * Compares two version strings
     * @param currentVersion The current version
     * @param otherVersion   The version to compare to
     * @return -1 if currentVersion is older than otherVersion, 0 if they are equal, 1 if currentVersion is newer than otherVersion
     */
    public int compareVersionStrings(String currentVersion, String otherVersion){
        String[] currentVersionParts = currentVersion.split("\\.");
        String[] otherVersionParts = otherVersion.split("\\.");

        // compare major version
        if(compareVersionPart(currentVersionParts[0], otherVersionParts[0]) == -1) {
            return -1;
        }
        // compare minor version
        if(compareVersionPart(currentVersionParts[1], otherVersionParts[1]) == -1) {
            return -1;
        }
        // compare patch version
        if(compareVersionPart(currentVersionParts[2], otherVersionParts[2]) == -1) {
            return -1;
        }
        return 0;
    }

    /**
     * Compares individual parts of a version
     * @param currentVersionPart the current version part
     * @param otherVersionPart the version part you're comparing to
     * @return -1 if current version part is older, 0 if current version part is equal, 1 if current version part is newer
     */
    public int compareVersionPart(String currentVersionPart, String otherVersionPart){
        try {
            int currentPart = Integer.parseInt(currentVersionPart);
            int otherPart = Integer.parseInt(otherVersionPart);
            return Integer.compare(currentPart, otherPart);
        }
        catch (NumberFormatException e){
            return 0;
        }
    }
}


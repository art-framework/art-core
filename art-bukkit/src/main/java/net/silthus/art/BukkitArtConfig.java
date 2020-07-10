package net.silthus.art;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import net.silthus.art.api.config.ArtConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class BukkitArtConfig extends ArtConfig {

    public static ArtConfig of(@NonNull File file, @NonNull String sectionKey) throws InvalidConfigurationException {

        if (!file.exists()) {
            throw new InvalidConfigurationException("Configuration file " + file.getAbsolutePath() + " does not exist.");
        }

        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            BukkitArtConfig artConfig = new BukkitArtConfig(file, yamlConfiguration, sectionKey);
            artConfig.loadAndSave();
            return artConfig;
        } catch (IOException | InvalidConfigurationException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    @Getter(AccessLevel.PACKAGE)
    private final File file;
    @Getter(AccessLevel.PACKAGE)
    private final YamlConfiguration fileConfiguration;
    @Getter(AccessLevel.PACKAGE)
    private final String sectionKey;
    @Getter(AccessLevel.PACKAGE)
    private final ConfigurationSection config;
    private final BukkitOptions options;

    BukkitArtConfig(File file, YamlConfiguration fileConfiguration, String sectionKey) {
        this.file = file;
        this.fileConfiguration = fileConfiguration;
        this.sectionKey = sectionKey;

        if (fileConfiguration.isConfigurationSection(sectionKey)) {
            this.config = fileConfiguration.getConfigurationSection(sectionKey);
        } else {
            this.config = fileConfiguration.createSection(sectionKey);
        }

        if (config.isConfigurationSection("options")) {
            this.options = new BukkitOptions(config.getConfigurationSection("options"));
        } else {
            this.options = new BukkitOptions(config.createSection("options"));
        }
    }

    @Override
    public String getId() {
        return getConfig().getString("id", super.getId());
    }

    @Override
    public String getParser() {
        return getConfig().getString("parser", super.getParser());
    }

    @Override
    public List<String> getArt() {
        return getConfig().getStringList("art");
    }

    @Override
    public void setId(String id) {
        getConfig().set("id", id);
    }

    @Override
    public void setParser(String parser) {
        getConfig().set("parser", parser);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void setOptions(Options options) {
        this.options.setOptions(options);
    }

    @Override
    public void setArt(List<String> art) {
        getConfig().set("art", art);
    }

    public void load() throws IOException, InvalidConfigurationException {
        getFileConfiguration().load(getFile());
        setId(getId());
        setParser(getParser());
        setArt(getArt());
        setOptions(getOptions());
    }

    /**
     * Saves this {@link ArtConfig} by calling the given {@link Consumer}.
     * @throws IOException if file cannot be saved
     */
    public void save() throws IOException {
        getFileConfiguration().set(getSectionKey(), getConfig());
        getFileConfiguration().save(getFile());
    }

    /**
     * Will first {@link #load()} and then {@link #save()}
     * the {@link ArtConfig} to the given {@link ConfigurationSection}.
     * Use this as a shortcut to initialize your config.
     *
     * @see #load()
     * @see #save()
     * @throws IOException if file cannot be saved or loaded
     * @throws InvalidConfigurationException if config is invalid
     */
    public void loadAndSave() throws IOException, InvalidConfigurationException {
        load();
        save();
    }

    static class BukkitOptions extends Options {

        @Getter(AccessLevel.PACKAGE)
        private final ConfigurationSection optionConfig;

        private BukkitOptions(ConfigurationSection optionConfig) {
            this.optionConfig = optionConfig;
        }

        private void setOptions(Options options) {
            setWorlds(options.getWorlds());
        }

        @Override
        public List<String> getWorlds() {
            return getOptionConfig().getStringList("worlds");
        }

        @Override
        public void setWorlds(List<String> worlds) {
            getOptionConfig().set("worlds", worlds);
        }
    }
}

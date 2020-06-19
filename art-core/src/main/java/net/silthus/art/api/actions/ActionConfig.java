package net.silthus.art.api.actions;

import de.exlll.configlib.annotation.ConfigurationElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.silthus.art.api.config.ArtConfigException;
import net.silthus.art.api.config.ArtObjectConfig;
import net.silthus.art.api.config.ConfigFieldInformation;
import net.silthus.art.util.ConfigUtil;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ActionConfig} holds general information about the execution
 * properties of the action. Like delay, cooldown, etc.
 *
 * @param <TConfig> custom config type of the action
 */
@Data
@ConfigurationElement
@EqualsAndHashCode(callSuper = true)
public class ActionConfig<TConfig> extends ArtObjectConfig<TConfig> {

    public static final Map<String, ConfigFieldInformation> CONFIG_FIELD_INFORMATION = new HashMap<>();

    static {
        try {
            CONFIG_FIELD_INFORMATION.putAll(ConfigUtil.getConfigFields(ActionConfig.class));
        } catch (ArtConfigException e) {
            e.printStackTrace();
        }
    }

    private String delay = "0s";
    private String cooldown = "0s";
    private boolean executeOnce = false;

    /**
     * The delay in milliseconds for this action.
     *
     * @return delay in milliseconds
     */
    public long getDelay() {
        throw new NotImplementedException();
    }

    /**
     * The cooldown in milliseconds for this action.
     *
     * @return cooldown in milliseconds
     */
    public long getCooldown() {
        throw new NotImplementedException();
    }
}

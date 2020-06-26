/*
 * Copyright 2020 ART-Framework Contributors (https://github.com/Silthus/art-framework)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.silthus.art.api.requirements;

import de.exlll.configlib.annotation.ConfigurationElement;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.silthus.art.api.config.ArtConfigException;
import net.silthus.art.api.config.ArtObjectConfig;
import net.silthus.art.api.config.ConfigFieldInformation;
import net.silthus.art.util.ConfigUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link RequirementConfig} holds general information about the execution
 * properties of the requirement.
 * e.g. if the {@link Requirement} should be negated
 *
 * @param <TConfig> custom config type of the requirement
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ConfigurationElement
@EqualsAndHashCode(callSuper = true)
public class RequirementConfig<TConfig> extends ArtObjectConfig<TConfig> {

    public static final Map<String, ConfigFieldInformation> CONFIG_FIELD_INFORMATION = new HashMap<>();

    static {
        try {
            CONFIG_FIELD_INFORMATION.putAll(ConfigUtil.getConfigFields(RequirementConfig.class));
        } catch (ArtConfigException e) {
            e.printStackTrace();
        }
    }

    private boolean persistent = false;
    private int count = 0;

    public RequirementConfig() {
    }

    public RequirementConfig(TConfig with) {
        super(with);
    }
}

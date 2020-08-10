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

package io.artframework.conf;

import io.artframework.*;
import io.artframework.annotations.ART;
import io.artframework.annotations.ConfigOption;
import io.artframework.integration.data.Player;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("ArtInformationTest")
class DefaultArtObjectMetaTest {

    @Nested
    @DisplayName("initialize()")
    class initialize {

        @SneakyThrows
        @Test
        @DisplayName("should extract information from class")
        void shouldExtractIdentifierFromClass() {

            DefaultArtObjectMeta<MyClassOptions> options = new DefaultArtObjectMeta<>(MyClassOptions.class);

            assertThat(options.initialize())
                .extracting(
                        ArtObjectMeta::identifier,
                        ArtObjectMeta::alias,
                        ArtObjectMeta::description,
                        ArtObjectMeta::configClass,
                        ArtObjectMeta::targetClass
                ).contains(
                        "class-options",
                        new String[]{"class"},
                        new String[]{"testing the ART annotation on classes"},
                        Optional.of(MyClassOptions.class),
                        Player.class
            );
        }

        @Test
        @SneakyThrows
        @DisplayName("should extract information from method")
        void shouldExtractOptionsFromMethod() {

            DefaultArtObjectMeta<MyMethodOptions> options = new DefaultArtObjectMeta<>(MyMethodOptions.class);

            assertThat(options.initialize())
                    .extracting(
                            ArtObjectMeta::identifier,
                            ArtObjectMeta::alias,
                            ArtObjectMeta::description,
                            ArtObjectMeta::configClass,
                            ArtObjectMeta::targetClass
                    ).contains(
                        "class-options",
                        new String[]{"class"},
                        new String[]{"testing the ART annotation on classes"},
                        Optional.of(MyMethodOptions.class),
                        Player.class
            );
        }

        @SneakyThrows
        @Test
        @DisplayName("should create config map from art object class")
        void shouldCreateConfigMap() {

            DefaultArtObjectMeta<MyClassOptions> options = new DefaultArtObjectMeta<>(MyClassOptions.class);

            assertThatCode(() -> assertThat(options.initialize().configMap())
                    .containsKey("cfg")
                    .extractingByKey("cfg")
                    .extracting(ConfigFieldInformation::defaultValue)
                    .isEqualTo("foo")
            ).doesNotThrowAnyException();
        }
    }

    @ART(value = "class-options", alias = "class", description = "testing the ART annotation on classes")
    public static class MyClassOptions implements Action<Player> {

        @ConfigOption
        private String cfg = "foo";

        @Override
        public Result execute(@NonNull Target<Player> target, @NonNull ExecutionContext<ActionContext<Player>> context) {
            return success();
        }
    }

    public static class MyMethodOptions implements Requirement<Player> {

        @Override
        @ART(value = "class-options", alias = "class", description = "testing the ART annotation on classes")
        public Result test(@NonNull Target<Player> target, @NonNull ExecutionContext<RequirementContext<Player>> context) {
            return success();
        }
    }

    public static class AlternateConfig {

        @ConfigOption
        private int configValue = 1337;

        public AlternateConfig() {
        }
    }
}
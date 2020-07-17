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

package net.silthus.art;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public interface Trigger extends ArtObject {

    default void trigger(String identifier, Predicate<ExecutionContext<?, TriggerContext>> context, Target<?>... targets) {
        ART.trigger(identifier, context, targets);
    }

    default void trigger(String identifier, Predicate<ExecutionContext<?, TriggerContext>> context, Object... targets) {
        trigger(identifier, context, Arrays.stream(targets)
                .map(Target::of)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Target[]::new));
    }

    default void trigger(String identifier, Target<?>... targets) {
        ART.trigger(identifier, targets);
    }

    default void trigger(String identifier, Object... targets) {
        trigger(identifier, Arrays.stream(targets)
                .map(Target::of)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Target[]::new));
    }
}

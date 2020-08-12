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

package io.artframework.impl;

import io.artframework.AbstractProvider;
import io.artframework.Scope;
import io.artframework.Target;
import io.artframework.TargetProvider;
import io.artframework.util.ReflectionUtil;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DefaultTargetProvider extends AbstractProvider implements TargetProvider {

    @SuppressWarnings("rawtypes")
    private final Map<Class<?>, Function> targetProviders = new HashMap<>();

    public DefaultTargetProvider(@NonNull Scope scope) {
        super(scope);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TTarget> Optional<Target<TTarget>> get(@Nullable TTarget source) {
        if (source == null) return Optional.empty();

        return ReflectionUtil.getEntryForTarget(source, targetProviders)
                .map(targetFunction -> (Target<TTarget>) targetFunction.apply(source));
    }

    @Override
    public <TTarget> boolean exists(@Nullable TTarget source) {
        return get(source).isPresent();
    }

    @Override
    public <TTarget> TargetProvider add(@NonNull Class<TTarget> sourceClass, @NonNull Function<TTarget, Target<TTarget>> targetProvider) {
        targetProviders.put(sourceClass, targetProvider);
        return this;
    }

    @Override
    public <TTarget> TargetProvider remove(@NonNull Class<TTarget> sourceClass) {
        targetProviders.remove(sourceClass);
        return this;
    }

    @Override
    public TargetProvider clear() {
        targetProviders.clear();
        return this;
    }
}
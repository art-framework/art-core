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

package net.silthus.art.api.trigger;

import com.google.inject.Inject;
import net.silthus.art.Scheduler;
import net.silthus.art.Storage;
import net.silthus.art.Target;
import net.silthus.art.Trigger;
import net.silthus.art.annotations.ArtObject;
import net.silthus.art.api.annotations.ActiveStorageProvider;
import net.silthus.art.api.factory.AbstractFactoryManager;

import javax.inject.Singleton;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Singleton
public class DefaultTriggerManager extends AbstractFactoryManager<TriggerFactory<?>> implements TriggerManager {

    private final Storage storage;
    @Inject(optional = true)
    private Scheduler scheduler;

    @Inject
    public DefaultTriggerManager(@ActiveStorageProvider Storage storage) {
        this.storage = storage;
    }

    @Override
    public Collection<TriggerFactory<?>> create(Trigger trigger) {

        Method[] methods = trigger.getClass().getDeclaredMethods();
        List<TriggerFactory<?>> factories = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(ArtObject.class))
                .map(method -> {
                    TriggerFactory<?> triggerFactory = new TriggerFactory<>(trigger, storage, scheduler);
                    triggerFactory.setMethod(method);
                    return triggerFactory;
                }).collect(Collectors.toList());

        if (factories.isEmpty()) {
            factories.add(new TriggerFactory<>(trigger, storage, scheduler));
        }

        return factories;
    }

    @Override
    public <TTarget> void addListener(String identifier, Class<TTarget> targetClass, TriggerListener<TTarget> listener) {
        getFactory(identifier).ifPresent(triggerFactory -> triggerFactory.addListener(targetClass, listener));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TConfig> void trigger(String identifier, Predicate<TriggerContext<TConfig>> predicate, Target<?>... targets) {
        List<TriggerContext<TConfig>> contextList = getFactory(identifier).map(TriggerFactory::getCreatedTrigger)
                .map(triggerContexts -> triggerContexts.stream()
                        .map(triggerContext -> (TriggerContext<TConfig>) triggerContext)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        for (Target<?> target : targets) {
            trigger(target, predicate, contextList);
        }
    }

    private <TTarget, TConfig> void trigger(Target<TTarget> target, Predicate<TriggerContext<TConfig>> predicate, List<TriggerContext<TConfig>> contextList) {
        contextList.forEach(context -> context.trigger(target, predicate));
    }
}
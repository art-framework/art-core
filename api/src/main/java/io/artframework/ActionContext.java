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

package io.artframework;

import io.artframework.conf.ActionConfig;
import io.artframework.impl.DefaultActionContext;
import lombok.NonNull;

import java.util.function.Consumer;

/**
 * The ActionContext wraps the actual {@link Action} and handles
 * the execution logic of the action.
 *
 * @param <TTarget> type of the target
 */
public interface ActionContext<TTarget> extends Action<TTarget>, ArtObjectContext<Action<TTarget>>, RequirementHolder, ActionHolder {

    static <TTarget> ActionContext<TTarget> of(
            @NonNull Scope scope,
            @NonNull ActionConfig config,
            @NonNull ActionFactory<TTarget> factory,
            @NonNull ConfigMap artObjectConfig
    ) {
        return new DefaultActionContext<>(scope, config, factory, artObjectConfig);
    }

    static <TTarget> ActionContext<TTarget> of(
            @NonNull Scope scope,
            @NonNull ArtObjectMeta<Action<TTarget>> meta,
            @NonNull Action<TTarget> action,
            @NonNull ActionConfig config
    ) {

        return new DefaultActionContext<>(scope, meta, action, config);
    }

    /**
     * Gets the config used by this {@link ActionContext}.
     *
     * @return config of this context
     */
    ActionConfig config();

    /**
     * Executes the action in this context and all nested actions using
     * the provided execution context and all targets in the context that match
     * the target type of the action.
     * <p>The context will be called sequentially per target that matches the type.
     * <p>As actions may be delayed a {@link FutureResult} is returned and must be
     * subscribed with the {@link FutureResult#onCompletion(Consumer)} to listen to
     * the completion of the action and all of its sub actions. The delay here may be
     * significant (minutes) depending on the configuration of the action.
     *
     * @param context the execution context that called the action context execution
     * @return the result of the execution
     */
    @SuppressWarnings("unchecked")
    default FutureResult execute(@NonNull ExecutionContext<?> context) {
        return context.targets().stream()
                .filter(target -> target.isTargetType(targetClass()))
                .map(target -> (Target<TTarget>) target)
                .map(target -> execute(target, context.next(this)))
                .reduce(FutureResult::combine)
                .orElse(FutureResult.empty());
    }

    @Override
    FutureResult execute(@NonNull Target<TTarget> target, @NonNull ExecutionContext<ActionContext<TTarget>> context);
}

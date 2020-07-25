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

import net.silthus.art.events.Event;
import net.silthus.art.events.EventManager;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class ART {

    private ART() {}

    private static final Configuration globalConfiguration = Configuration.create();

    /**
     * Gets the global {@link Configuration} configured for all static methods in this class.
     * You can use the {@link Configuration#derive()} method to clone the configuration
     * and provide a local modified copy of it to the {@link ArtBuilder}
     * or any other object in the ART-Framework.
     *
     * @return the global {@link Configuration}
     */
    public static Configuration configuration() {
        return globalConfiguration;
    }

    // TODO: javadoc
    public static ArtProvider register() {
        return configuration().art();
    }

    // TODO: javadoc
    public static ArtFinder find() {
        return configuration().art().find();
    }

    public static ActionProvider actions() {
        return configuration().art().actions();
    }

    public static RequirementProvider requirements() {
        return configuration().art().requirements();
    }

    public static TriggerProvider trigger() {
        return configuration().art().trigger();
    }

    // TODO: javadoc
    public static ArtBuilder builder() {
        return builder(configuration());
    }

    // TODO: javadoc
    public static ArtBuilder builder(Configuration configuration) {
        return ArtBuilder.of(configuration);
    }

    /**
     * Use this method to create and load an {@link ArtContext} from your config.
     * <br>
     * You can then use the {@link ArtContext} to invoke {@link Action}s by calling
     * {@link ArtContext#execute(Target)} or to check for requirements by calling {@link ArtContext#test(Target)}.
     * <br>
     * This is actually a shortcut to {@link ArtBuilderParser#load(Object)}. You can also call the
     * builder directly ({@link #builder()}) and fine tune how you want to load and parse your ART.
     *
     * @param lines a list of strings that contain the ART you want to load
     * @return ART containing the parsed art lines
     * @see ArtBuilder
     */
    public static ArtContext load(List<String> lines) {
        return builder().parser().load(lines).build();
    }

    /**
     * This is just an alias for the {@link #load(List)} function.
     *
     * @param artLines a list of strings that contain the ART you want to create
     * @return ART containing the parsed art lines
     * @see #load(List)
     */
    public static ArtContext create(List<String> artLines) {
        return load(artLines);
    }

    /**
     * Executes the trigger with the given identifier sourced to the given targets
     * checking their predicates before executing the trigger.
     * <p>
     * This is just a convenience method delegating to {@link TriggerProvider#trigger(String, TriggerTarget[])}.
     *
     * @param identifier the identifier or alias of the trigger
     * @param targets the targets that executed the trigger
     * @return the trigger result
     * @see TriggerProvider#trigger(String, TriggerTarget[])
     */
    public static CombinedResult trigger(String identifier, TriggerTarget<?>... targets) {
        return configuration().trigger().trigger(identifier, targets);
    }

    /**
     * Executes the trigger with the given identifier sourced to the given targets.
     * <p>
     * This is just a convenience method delegating to {@link TriggerProvider#trigger(String, Target[])}.
     *
     * @param identifier the identifier or alias of the trigger
     * @param targets the targets that executed the trigger
     * @return the trigger result
     * @see TriggerProvider#trigger(String, Target[])
     */
    public static CombinedResult trigger(String identifier, Target<?>... targets) {
        return configuration().trigger().trigger(identifier, targets);
    }

    /**
     * Tries to get a valid {@link Target} wrapper for the given object.
     * Delegates to {@link TargetProvider#get(Object)}.
     *
     * @param target The source object that should be wrapped as a {@link Target}
     * @param <TTarget> type of the target source
     * @return wrapped {@link Target} or an empty {@link Optional} if the source was null or no target is found
     * @see TargetProvider#get(Object)
     */
    public static <TTarget> Optional<Target<TTarget>> getTarget(@Nullable TTarget target) {
        return configuration().targets().get(target);
    }

    public static <TEvent extends Event> TEvent callEvent(TEvent event) {
        return EventManager.callEvent(event);
    }
}

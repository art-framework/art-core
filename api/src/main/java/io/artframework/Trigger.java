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

/**
 * A Trigger is used to trigger actions and check requirements.
 * <p>Use this interface to mark your class as a trigger and add the {@link io.artframework.annotations.ART} annotation.
 * <p>If your trigger has requirements implement the {@link Requirement} interface.
 * <p>Make sure that you register your <code>Trigger</code> with the {@link TriggerProvider}.
 * <p>Execute/call the trigger by using the {@link Scope#trigger(Class)} or {@link ART#trigger(Class)} method to
 * create a {@link TriggerExecution}. Then call {@link TriggerExecution#execute()} after adding the targets to the trigger.
 * <pre>{@code
 * // register the trigger
 * scope.register().trigger().add(MyTrigger.class);
 * // and then call the trigger like this
 * scope.trigger(MyTrigger.class).with(player).execute();
 * }</pre>
 */
public interface Trigger extends ArtObject {

}

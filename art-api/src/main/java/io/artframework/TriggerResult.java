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
 * The trigger result is returned after a trigger was executed and contains
 * information and data about the listeners that were listening to the trigger.
 */
public interface TriggerResult {

    static TriggerResult success() {
        return null;
    }

    static TriggerResult failure() {
        return null;
    }

    static TriggerResult failure(ErrorCode errorCode) {
        return null;
    }

    boolean isEmpty();
}
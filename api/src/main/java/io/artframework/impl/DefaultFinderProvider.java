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

import com.google.common.collect.ImmutableList;
import io.artframework.AbstractProvider;
import io.artframework.Finder;
import io.artframework.FinderProvider;
import io.artframework.Scope;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultFinderProvider extends AbstractProvider implements FinderProvider {

    private final List<Finder> finders = new ArrayList<>();

    public DefaultFinderProvider(@NonNull Scope scope) {
        super(scope);

        for (Finder defaultFinder : Finder.defaults(scope)) {
            add(defaultFinder);
        }
    }

    @Override
    public FinderProvider add(Finder finder) {
        finders.add(finder);
        return this;
    }

    @Override
    public FinderProvider remove(Finder finder) {
        finders.remove(finder);
        return this;
    }

    @Override
    public FinderProvider clear() {
        finders.clear();
        return this;
    }

    @Override
    public Collection<Finder> all() {
        return ImmutableList.copyOf(finders);
    }
}

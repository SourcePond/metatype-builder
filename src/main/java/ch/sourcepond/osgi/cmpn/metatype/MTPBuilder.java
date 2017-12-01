/*Copyright (C) 2017 Roland Hauser, <sourcepond@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package ch.sourcepond.osgi.cmpn.metatype;

import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Locale.getDefault;
import static java.util.Objects.requireNonNull;
import static javax.xml.bind.JAXB.unmarshal;

public class MTPBuilder {
    private final Set<String> locales = new HashSet<>();
    private final Map<String, Map<String, OCDBuilder>> ocdBuilders = new HashMap<>();

    public OCDBuilder ocd(final String pId, final String pName) {
        return ocd(pId, pName, getDefault().toString());
    }

    public OCDBuilder ocd(final String pId, final String pName, final String pLocale) {
        return new OCDBuilder().init(this, pLocale, pId, pName);
    }

    public <T extends Annotation> OCDBuilder ocd(final Class<T> pConfigDefinition) throws IOException {
        final String path = format("OSGI-INF/metatype/%s.xml", pConfigDefinition.getName());
        try (final InputStream in = requireNonNull(requireNonNull(pConfigDefinition, "Config definition is null").
                getClassLoader().getResourceAsStream(path), format("Resource not found: %s", path))) {
            return unmarshal(in, MetaData.class).find(this, pConfigDefinition);
        }
    }

    void addOCD(final String pLocale, final OCDBuilder pOcd) {
        requireNonNull(pLocale, "Locale is null");
        requireNonNull(pOcd.getId(), "OCD ID is null");
        ocdBuilders.computeIfAbsent(pLocale, l -> new HashMap<>()).put(pOcd.getId(), pOcd);
    }

    public MetaTypeProvider build() {
        final Map<String, Map<String, OCD>> localeToBuilderMap = new HashMap<>();
        ocdBuilders.entrySet().forEach(outer -> {
            final Map<String, OCD> idToOcdMap = new HashMap<>();
            outer.getValue().entrySet().forEach(inner -> {
                localeToBuilderMap.computeIfAbsent(outer.getKey(), k -> new HashMap<>()). put(
                        inner.getKey(), inner.getValue().build());
            });
        });
        return new MTP(locales.toArray(new String[0]), localeToBuilderMap);
    }
}

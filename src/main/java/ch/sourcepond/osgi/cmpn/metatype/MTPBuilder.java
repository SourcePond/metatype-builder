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

import javax.xml.bind.JAXB;
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

public class MTPBuilder {
    private final Set<String> locales = new HashSet<>();
    private final Map<String, Map<String, ObjectClassDefinition>> ocds = new HashMap<>();

    public OCDBuilder ocd(final String pId, final String pName) {
        return ocd(pId, pName, getDefault().toString());
    }

    public OCDBuilder ocd(final String pId, final String pName, final String pLocale) {
        return new OCDBuilder(this, pLocale).id(pId).name(pName);
    }

    void addOCD(final String pLocale, final OCD pOcd) {
        requireNonNull(pLocale, "Locale is null");
        requireNonNull(pOcd.getID(), "OCD is null");
        ocds.computeIfAbsent(pLocale, l -> new HashMap<>()).put(pOcd.getID(), pOcd);
    }

    public <T extends Annotation> OCDBuilder ocd(final Class<T> pConfigDefinition) throws IOException {
        final String path = format("/OSGI-INF/metatype/%s.xml", pConfigDefinition.getName());
        try (final InputStream in = requireNonNull(requireNonNull(pConfigDefinition, "Config definition is null").
                        getClassLoader().
                        getResourceAsStream(format("/OSGI-INF/metatype/%s.xml", pConfigDefinition.getName())),
                format("Resource not found: %s", path))) {
            return JAXB.unmarshal(in, MetaData.class).get(pConfigDefinition);
        }
    }

    public MetaTypeProvider build() {
        return new MTP(locales.toArray(new String[0]), ocds);
    }
}

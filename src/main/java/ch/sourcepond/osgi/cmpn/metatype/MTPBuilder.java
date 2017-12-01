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

import org.osgi.framework.Bundle;
import org.osgi.service.metatype.MetaTypeProvider;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static javax.xml.bind.JAXB.unmarshal;
import static org.osgi.framework.Constants.BUNDLE_LOCALIZATION;
import static org.osgi.framework.Constants.BUNDLE_LOCALIZATION_DEFAULT_BASENAME;
import static org.osgi.framework.FrameworkUtil.getBundle;

@XmlRootElement(namespace = "metatype")
public class MTPBuilder {
    private final Map<String, OCDBuilder> ocdBuilderMap = new HashMap<>();
    private final List<OCDBuilder> ocdBuilders = new LinkedList<>();
    private Class<?> resourcesAccessor = getClass();
    private String localization;

    public static <T extends Annotation> MTPBuilder load(final Class<T> pConfigDefinition) {
        final String path = format("/OSGI-INF/metatype/%s.xml", pConfigDefinition.getName());
        final URL url = requireNonNull(pConfigDefinition.getResource(path), format("File %s not found in classpath", path));

        final MTPBuilder mtpBuilder;
        try (final InputStream in = url.openStream()) {
            mtpBuilder = unmarshal(in, MTPBuilder.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
        mtpBuilder.resourcesAccessor = pConfigDefinition;
        return mtpBuilder;
    }

    @XmlAttribute
    public String getLocalization() {
        return localization;
    }

    @XmlElement(name = "OCD")
    public List<OCDBuilder> getOCD() {
        return ocdBuilders;
    }

    public OCDBuilder ocd(final String pId, final String pName) {
        return ocdBuilderMap.computeIfAbsent(pId, id -> new OCDBuilder().init(this, pId, pName));
    }

    void addOCD(final OCDBuilder pOcd) {
        ocdBuilderMap.put(pOcd.getId(), pOcd);
    }

    public MetaTypeProvider build() {
        final ConcurrentMap<String, OCD> ocdMap = new ConcurrentHashMap<>();
        ocdBuilderMap.entrySet().forEach(entry -> {
            ocdMap.put(entry.getKey(), entry.getValue().build());
        });

        final Bundle bundle = requireNonNull(getBundle(resourcesAccessor), format("No bundle for %s", resourcesAccessor));
        String baseName = localization;
        if (baseName == null) {
            baseName = bundle.getHeaders().get(BUNDLE_LOCALIZATION);
        }
        if (baseName == null) {
            baseName = BUNDLE_LOCALIZATION_DEFAULT_BASENAME;
        }
        int index = baseName.indexOf('/');
        String pattern = baseName.substring(index + 1) + "*.properties";
        String path = index == -1 ? "" : baseName.substring(0, index);
        final Enumeration<URL> urls = bundle.findEntries(path, pattern, false);

        String[] locales = null;
        if (urls != null) {
            final List<String> localeList = new LinkedList<>();
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                path = url.getPath();
                index = path.lastIndexOf('/');
                pattern = index == -1 ? path : path.substring(index + 1);

                index = pattern.indexOf('_');
                if (index != -1) {
                    final String[] items = pattern.substring(index + 1).replace(".properties", "").split("_");
                    Locale locale;
                    switch (items.length) {
                        case 1: {
                            locale = new Locale(items[0]);
                            break;
                        }
                        case 2: {
                            locale = new Locale(items[0], items[1]);
                            break;
                        }
                        case 3: {
                            locale = new Locale(items[0], items[1], items[2]);
                            break;
                        }
                        default: {
                            locale = null;
                        }
                    }

                    if (locale != null) {
                        localeList.add(locale.toString());
                    }
                }
            }
            locales = localeList.toArray(new String[localeList.size()]);
        }

        return new MTP(new LocalizationFactory(baseName, locales), ocdMap);
    }
}

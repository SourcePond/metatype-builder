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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private transient Bundle bundle;
    private String localization;

    MTPBuilder() {
    }

    public static MTPBuilder create(final Bundle pBundle) {
        final MTPBuilder builder = new MTPBuilder();
        builder.setBundle(pBundle);
        return builder;
    }

    public static <T extends Annotation> MTPBuilder create(final Class<T> pConfigDefinition) {
        final String path = format("/OSGI-INF/metatype/%s.xml", pConfigDefinition.getName());
        final URL url = requireNonNull(pConfigDefinition.getResource(path), format("File %s not found in classpath", path));

        final MTPBuilder mtpBuilder;
        try (final InputStream in = url.openStream()) {
            mtpBuilder = unmarshal(in, MTPBuilder.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
        mtpBuilder.initAfterUnmarshal(pConfigDefinition.getClassLoader());
        mtpBuilder.setBundle(getBundle(pConfigDefinition));
        return mtpBuilder;
    }

    private void initAfterUnmarshal(final ClassLoader pSourceLoader) {
        ocdBuilders.forEach(ocdBuilder -> {
            ocdBuilder.initAfterUnmarshal(this, pSourceLoader);
            ocdBuilderMap.put(ocdBuilder.getId(), ocdBuilder);
        });
    }

    void setBundle(final Bundle pBundle) {
        bundle = requireNonNull(pBundle, "Bundle is null");
    }

    @XmlAttribute
    String getLocalization() {
        return localization;
    }

    void setLocalization(final String pLocalization) {
        localization = pLocalization;
    }

    @XmlElement(name = "OCD")
    List<OCDBuilder> getOCD() {
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

        String baseName = localization;
        if (baseName == null) {
            baseName = bundle.getHeaders().get(BUNDLE_LOCALIZATION);
        }
        if (baseName == null) {
            baseName = BUNDLE_LOCALIZATION_DEFAULT_BASENAME;
        }

        return new MTP(new LocalizationFactory(baseName, bundle), ocdMap);
    }
}

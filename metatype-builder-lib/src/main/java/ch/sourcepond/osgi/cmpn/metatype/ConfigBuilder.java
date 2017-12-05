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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Objects.requireNonNull;

public class ConfigBuilder<T extends Annotation> {
    private final Dictionary<String, ?> config;
    private final Class<T> configAnnotation;
    private final Map<String, ADBuilder<?, ?>> ad;

    ConfigBuilder(final Dictionary<String, ?> pConfig, final Class<T> pConfigAnnotation, final Map<String, ADBuilder<?, ?>> pAd) {
        config = requireNonNull(pConfig, "Config is null");
        configAnnotation = requireNonNull(pConfigAnnotation, "Config annotation is null");
        ad = pAd;
    }

    private T toConfig() {
        final Map<String, Object> map = new HashMap<>();
        final Enumeration<String> keys = config.keys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            final Object value = config.get(key);

            if (!ad.containsKey(key)) {
                throw new IllegalArgumentException(format("Unknown property found: key=%s, value=%s", key, value));
            }

            map.put(key, config.get(key));
        }
        return (T) newProxyInstance(configAnnotation.getClassLoader(), new Class<?>[]{configAnnotation},
                (proxy, method, args) -> map.get(method.getName()));
    }

    private T validate(T pUnvalidatedConfig) {
        for (final Method m : configAnnotation.getDeclaredMethods()) {
            try {
                final Object value = m.invoke(pUnvalidatedConfig);
                final ADBuilder<?, ?> builder = ad.get(m.getName());
                if (value == null && builder.isRequired()) {
                    throw new IllegalArgumentException(format("Property %s is required!", m.getName()));
                }
            } catch (final Exception e) {
                throw new IllegalArgumentException(format("Property %s could not be retrieved!", m.getName()), e);
            }
        }
        return pUnvalidatedConfig;
    }

    public T build() {
        return validate(toConfig());
    }
}

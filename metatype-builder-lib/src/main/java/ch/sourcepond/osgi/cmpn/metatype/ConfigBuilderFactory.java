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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ConfigBuilderFactory {

    public <T extends Annotation> ConfigBuilder<T> create(final Class<T> pConfigAnnotation, final Dictionary<String, ?> pConfig) {
        final Dictionary<String, ?> config = pConfig == null ? new Hashtable<>() : pConfig;
        final OCDBuilder ocdBuilder = MTPBuilder.create(pConfigAnnotation).ocd(pConfigAnnotation.getName(),
                pConfigAnnotation.getSimpleName());
        final Map<String, ADBuilder<?, ?>> ad = new HashMap<>();
        for (final ADBuilder<?, ?> adBuilder : ocdBuilder.getAD()) {
            ad.put(adBuilder.getId(), adBuilder);
        }
        return new ConfigBuilder<>(config, pConfigAnnotation, ad);
    }
}

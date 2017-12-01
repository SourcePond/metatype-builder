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

import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;
import static java.util.Locale.getDefault;

final class MTP implements MetaTypeProvider {
    private final LocalizationFactory localizationFactory;
    private final ConcurrentMap<String, OCD> ocds;

    public MTP(final LocalizationFactory pLocalizationFactory, final ConcurrentMap<String, OCD> pOcds) {
        localizationFactory = pLocalizationFactory;
        ocds = pOcds;
    }

    @Override
    public ObjectClassDefinition getObjectClassDefinition(final String pId, final String pLocale) {
        final String locale = pLocale == null ? getDefault().toString() : pLocale;

        final OCD ocd = ocds.get(pId);
        if (ocd == null) {
            throw new IllegalArgumentException(format("No OCD found for id '%s'", pId));
        }
        return ocd.localize(localizationFactory.create(locale));
    }

    @Override
    public String[] getLocales() {
        return localizationFactory.getLocales();
    }
}

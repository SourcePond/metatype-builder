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

import java.util.Map;

import static java.lang.String.format;
import static java.util.Locale.getDefault;

final class MTP implements MetaTypeProvider {
    private final String[] locales;
    private final Map<String, Map<String, OCD>> ocds;

    public MTP(final String[] pLocales, final Map<String, Map<String, OCD>> pOcds) {
        locales = pLocales;
        ocds = pOcds;
    }

    @Override
    public ObjectClassDefinition getObjectClassDefinition(final String pId, final String pLocale) {
        final String locale = pLocale == null ? getDefault().toString() : pLocale;
        final Map<String, OCD> subOCDs = ocds.get(locale);
        if (subOCDs == null) {
            throw new IllegalArgumentException(format("No OCD found for locale '%s'", locale));
        }
        final ObjectClassDefinition ocd = subOCDs.get(pId);
        if (ocd == null) {
            throw new IllegalArgumentException(format("No OCD found for id '%s'", pId));
        }
        return ocd;
    }

    @Override
    public String[] getLocales() {
        return locales;
    }
}

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

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Collections.sort;

class LocalizationFactory {
    private static final int LANGUAGE = 0;
    private static final int COUNTRY = 1;
    private static final int VARIANT = 2;
    private final String baseName;
    private final String[] supportedLocales;

    public LocalizationFactory(final String pBaseName, final Bundle pBundle) {
        baseName = pBaseName;
        supportedLocales = createLocales(pBundle);
    }

    private String[] createLocales(final Bundle pBundle) {
        int index = baseName.lastIndexOf('/');
        String pattern = baseName.substring(index + 1) + "*.properties";
        String path = index == -1 ? "" : baseName.substring(0, index);
        final Enumeration<URL> urls = pBundle.findEntries(path, pattern, false);

        final List<String> localeList = new LinkedList<>();
        if (urls != null) {
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                path = url.getPath();
                index = path.lastIndexOf('/');
                addLocale(index == -1 ? path : path.substring(index + 1), localeList);
            }
        }
        sort(localeList);
        return localeList.isEmpty() ? null : localeList.toArray(new String[0]);
    }

    private void addLocale(final String pattern, final List<String> pLocales) {
        final int index = pattern.indexOf('_');
        if (index != -1) {
            final String[] items = pattern.substring(index + 1).replace(".properties", "").split("_");
            Locale locale;
            switch (items.length - 1) {
                case LANGUAGE: {
                    locale = new Locale(items[LANGUAGE]);
                    break;
                }
                case COUNTRY: {
                    locale = new Locale(items[LANGUAGE], items[COUNTRY]);
                    break;
                }
                case VARIANT: {
                    locale = new Locale(items[LANGUAGE], items[COUNTRY], items[VARIANT]);
                    break;
                }
                default: {
                    locale = null;
                }
            }

            if (locale != null) {
                pLocales.add(locale.toString());
            }
        }
    }

    public String[] getLocales() {
        return supportedLocales;
    }

    public Localization create(final String pLocale) {
        return new Localization(ResourceBundle.getBundle(baseName, new Locale(pLocale)), pLocale);
    }
}

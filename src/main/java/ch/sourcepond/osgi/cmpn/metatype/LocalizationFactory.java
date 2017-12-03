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
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class LocalizationFactory {
    private volatile String[] supportedLocales;
    private final String baseName;
    private final Bundle bundle;

    public LocalizationFactory(final String pBaseName, final Bundle pBundle) {
        baseName = pBaseName;
        bundle = pBundle;
    }

    public String[] getLocales() {
        if (supportedLocales == null) {
            synchronized (this) {
                if (supportedLocales == null) {
                    int index = baseName.indexOf('/');
                    String pattern = baseName.substring(index + 1) + "*.properties";
                    String path = index == -1 ? "" : baseName.substring(0, index);
                    final Enumeration<URL> urls = bundle.findEntries(path, pattern, false);

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
                        supportedLocales = localeList.toArray(new String[localeList.size()]);
                    }
                }
            }
        }
        return supportedLocales;
    }

    public Localizer create(final String pLocale) {
        return new Localizer(ResourceBundle.getBundle(baseName, new Locale(pLocale)), pLocale);
    }
}

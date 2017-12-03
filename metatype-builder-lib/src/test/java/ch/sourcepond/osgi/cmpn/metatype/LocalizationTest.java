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

import org.junit.Test;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LocalizationTest {
    private static final String EXPECTED_KEY = "someKey";
    private static final String EXPECTED_LOCALE = "de_CH";
    private static final String EXPECTED_LOCALIZED_VALUE = "someLocalizedValue";
    private final ResourceBundle bundle = ResourceBundle.getBundle(getClass().getSimpleName());
    private final Localization localization = new Localization(bundle, EXPECTED_LOCALE);

    @Test
    public void getLocale() {
        assertEquals(EXPECTED_LOCALE, localization.getLocale());
    }

    @Test
    public void localize() {
        assertEquals(EXPECTED_LOCALIZED_VALUE, localization.localize("%" + EXPECTED_KEY));
        assertEquals(EXPECTED_KEY, localization.localize(EXPECTED_KEY));
        assertNull(localization.localize(null));
    }
}

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OptionTest {
    private final String EXPECTED_LABEL = "someLabel";
    private final String EXPECTED_LOCALIZED_LABEL = "localizedValue";
    private final String EXPECTED_VALUE = "someValue";
    private final Localization localization = mock(Localization.class);
    private final Option option = new Option(EXPECTED_LABEL, EXPECTED_VALUE);

    @Test
    public void localize() {
        when(localization.localize(EXPECTED_LABEL)).thenReturn(EXPECTED_LOCALIZED_LABEL);
        final Option localizedOption = option.localize(localization);
        assertNotSame(option, localizedOption);
        assertEquals(EXPECTED_VALUE, localizedOption.getValue());
        assertEquals(EXPECTED_LOCALIZED_LABEL, localizedOption.getLabel());
    }
}

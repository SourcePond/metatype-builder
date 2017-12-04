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

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OptionBuilderTest {
    private static final String EXPECTED_LABEL = "someLabel";
    private static final String EXPECTED_VALUE = "someValue";
    private final List<OptionBuilder<String, Integer>> options = mock(List.class);
    private final ADBuilder<String, Integer> parent = mock(ADBuilder.class);
    private final OptionBuilder<String, Integer> builder = new OptionBuilder<>();

    @Before
    public void setup() {
        when(parent.getOption()).thenReturn(options);
    }

    @Test
    public void setParent() {
        builder.setParent(parent).label("any").value("any");
        assertSame(parent, builder.add());
    }

    @Test
    public void label() {
        assertSame(builder, builder.label(EXPECTED_LABEL));
        assertEquals(EXPECTED_LABEL, builder.getLabel());
    }

    @Test
    public void value() {
        assertSame(builder, builder.value(EXPECTED_VALUE));
        assertEquals(EXPECTED_VALUE, builder.getValue());
    }

    @Test(expected = NullPointerException.class)
    public void setLabelNullValue() {
        builder.setLabel(null);
    }

    @Test(expected = NullPointerException.class)
    public void setValueNullValue() {
        builder.setValue(null);
    }

    @Test(expected = NullPointerException.class)
    public void addLabelIsNull() {
        builder.value(EXPECTED_VALUE).add();
    }

    @Test(expected = NullPointerException.class)
    public void addValueIsNull() {
        builder.label(EXPECTED_LABEL).add();
    }

    @Test
    public void build() {
        final Option option = builder.setParent(parent).label(EXPECTED_LABEL).value(EXPECTED_VALUE).build();
        assertEquals(EXPECTED_LABEL, option.getLabel());
        assertEquals(EXPECTED_VALUE, option.getValue());
    }

    @Test
    public void add() {
        assertSame(parent, builder.setParent(parent).label(EXPECTED_LABEL).value(EXPECTED_VALUE).add());
        verify(options).add(builder);
    }
}

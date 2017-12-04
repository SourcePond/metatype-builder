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
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ADBuilderTest {
    private static final String EXPECTED_ID = "someId";
    private static final Type<String, Integer> EXPECTED_TYPE = Type.STRING;
    private static final int EXPECTED_CARDINALITY = -34;
    private static final String EXPECTED_NAME = "someName";
    private static final String EXPECTED_DESCRIPTION = "someDescription";
    private static final String EXPECTED_MAX = "someMax";
    private static final String EXPECTED_MIN = "someMin";
    private final OCDBuilder parent = mock(OCDBuilder.class);
    private final ADBuilder<String, Integer> builder = new ADBuilder<>();

    @Test
    public void initAfterUnmarshal() {
        final OptionBuilder<String, Integer> optionBuilder = mock(OptionBuilder.class);
        builder.getOption().add(optionBuilder);
        builder.initAfterUnmarshal(parent);
        assertSame(parent, builder.id(EXPECTED_ID).type(EXPECTED_TYPE).add());
        verify(optionBuilder).setParent(builder);
    }

    @Test(expected = NullPointerException.class)
    public void addIdNotSet() {
        builder.setParent(parent).type(EXPECTED_TYPE).add();
    }

    @Test(expected = NullPointerException.class)
    public void addTypeNotSet() {
        builder.setParent(parent).id(EXPECTED_ID).add();
    }

    @Test
    public void id() {
        assertEquals(EXPECTED_ID, builder.id(EXPECTED_ID).getId());
    }

    @Test
    public void type() {
        assertEquals(EXPECTED_TYPE, builder.type(Type.STRING).getType());
    }

    @Test
    public void cardinality() {
        assertEquals(EXPECTED_CARDINALITY, builder.cardinality(EXPECTED_CARDINALITY).getCardinality());
    }

    @Test
    public void name() {
        assertEquals(EXPECTED_NAME, builder.name(EXPECTED_NAME).getName());
    }

    @Test
    public void description() {
        assertEquals(EXPECTED_DESCRIPTION, builder.description(EXPECTED_DESCRIPTION).getDescription());
    }

    @Test
    public void max() {
        assertEquals(EXPECTED_MAX, builder.max(EXPECTED_MAX).getMax());
    }

    @Test
    public void min() {
        assertEquals(EXPECTED_MIN, builder.min(EXPECTED_MIN).getMin());
    }
}

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
import org.osgi.framework.Bundle;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ConfigBuilderFactoryTest {
    private static final String GET_STRING = "getString";
    private static final String GET_LONG = "getLong";
    private static final String GET_DOUBLE = "getDouble";
    private static final String GET_FLOAT = "getFloat";
    private static final String GET_INTEGER = "getInteger";
    private static final String GET_BYTE = "getByte";
    private static final String GET_CHARACTER = "getCharacter";
    private static final String GET_BOOLEAN = "getBoolean";
    private static final String GET_SHORT = "getShort";
    private static final String GET_PASSWORD = "getPassword";
    private static final String GET_STRINGS = "getStrings";
    private static final String GET_LONGS = "getLongs";
    private static final String GET_DOUBLES = "getDoubles";
    private static final String GET_FLOATS = "getFloats";
    private static final String GET_INTEGERS = "getIntegers";
    private static final String GET_BYTES = "getBytes";
    private static final String GET_CHARACTERS = "getCharacters";
    private static final String GET_BOOLEANS = "getBooleans";
    private static final String GET_SHORTS = "getShorts";
    private static final String GET_TIME_UNIT = "getTimeUnit";
    private static final String GET_TIME_UNITS = "getTimeUnits";
    private static final String GET_PASSWORDS = "getPasswords";
    private static final String STRING_VALUE = "getString";
    private static final Long LONG_VALUE = 1L;
    private static final Double DOUBLE_VALUE = 1d;
    private static final Float FLOAT_VALUE = 1f;
    private static final Integer INTEGER_VALUE = 1;
    private static final Byte BYTE_VALUE = 1;
    private static final Character CHARACTER_VALUE = 'a';
    private static final Boolean BOOLEAN_VALUE = true;
    private static final Short SHORT_VALUE = 1;
    private static final String PASSWORD_VALUE = "somePassword";
    private static final String[] STRING_VALUES = new String[]{"password1", "password2"};
    private static final long[] LONG_VALUES = new long[]{1, 2};
    private static final double[] DOUBLE_VALUES = new double[]{1, 2};
    private static final float[] FLOAT_VALUES = new float[]{1, 2};
    private static final int[] INTEGER_VALUES = new int[]{1, 2};
    private static final byte[] BYTE_VALUES = new byte[]{1, 2};
    private static final char[] CHARACTER_VALUES = new char[]{1, 2};
    private static final boolean[] BOOLEAN_VALUES = new boolean[]{true, true};
    private static final short[] SHORT_VALUES = new short[]{1, 2};
    private static final TimeUnit TIME_UNIT_VALUE = TimeUnit.DAYS;
    private static final TimeUnit[] TIME_UNIT_VALUES = new TimeUnit[]{TimeUnit.DAYS, TimeUnit.HOURS};
    private static final String[] PASSWORD_VALUES = new String[]{"password1", "password2"};
    private final Bundle bundle = mock(Bundle.class);
    private final TestClassLoader cl = new TestClassLoader(bundle);
    private final ConfigBuilderFactory factory = new ConfigBuilderFactory();
    private final Dictionary<String, Object> props = new Hashtable<>();
    private Object config;

    @Before
    public void setup() throws Exception {
        props.put(GET_STRING, STRING_VALUE);
        props.put(GET_LONG, LONG_VALUE);
        props.put(GET_DOUBLE, DOUBLE_VALUE);
        props.put(GET_FLOAT, FLOAT_VALUE);
        props.put(GET_INTEGER, INTEGER_VALUE);
        props.put(GET_BYTE, BYTE_VALUE);
        props.put(GET_CHARACTER, CHARACTER_VALUE);
        props.put(GET_BOOLEAN, BOOLEAN_VALUE);
        props.put(GET_SHORT, SHORT_VALUE);
        props.put(GET_PASSWORD, PASSWORD_VALUE);
        props.put(GET_STRINGS, STRING_VALUES);
        props.put(GET_LONGS, LONG_VALUES);
        props.put(GET_DOUBLES, DOUBLE_VALUES);
        props.put(GET_FLOATS, FLOAT_VALUES);
        props.put(GET_INTEGERS, INTEGER_VALUES);
        props.put(GET_BYTES, BYTE_VALUES);
        props.put(GET_CHARACTERS, CHARACTER_VALUES);
        props.put(GET_BOOLEANS, BOOLEAN_VALUES);
        props.put(GET_SHORTS, SHORT_VALUES);
        props.put(GET_TIME_UNIT, TIME_UNIT_VALUE);
        props.put(GET_TIME_UNITS, TIME_UNIT_VALUES);
        props.put(GET_PASSWORDS, PASSWORD_VALUES);
        config = factory.create((Class) cl.loadClass(TestConfigurationAsAnnotation.class.getName()), props).build();
    }

    private <T> T get(final String pMethodName) throws Exception {
        return (T) config.getClass().getDeclaredMethod(pMethodName).invoke(config);
    }

    @Test
    public void buildWithDefaults() throws Exception {
        config = factory.create((Class) cl.loadClass(TestConfigurationAsAnnotation.class.getName()), null).build();
        assertEquals("one", get(GET_STRING));
        assertEquals(LONG_VALUE, get(GET_LONG));
        assertEquals(DOUBLE_VALUE, get(GET_DOUBLE));
        assertEquals(FLOAT_VALUE, get(GET_FLOAT));
        assertEquals(INTEGER_VALUE, get(GET_INTEGER));
        assertEquals(BYTE_VALUE, get(GET_BYTE));
        assertEquals(CHARACTER_VALUE, get(GET_CHARACTER));
        assertEquals(BOOLEAN_VALUE, get(GET_BOOLEAN));
        assertEquals(SHORT_VALUE, get(GET_SHORT));
        assertEquals("password", get(GET_PASSWORD));
        assertArrayEquals(new String[]{"one", "two"}, get(GET_STRINGS));
        assertArrayEquals(LONG_VALUES, get(GET_LONGS));
        assertArrayEquals(DOUBLE_VALUES, get(GET_DOUBLES), 0);
        assertArrayEquals(FLOAT_VALUES, get(GET_FLOATS), 0);
        assertArrayEquals(INTEGER_VALUES, get(GET_INTEGERS));
        assertArrayEquals(BYTE_VALUES, get(GET_BYTES));
        assertArrayEquals(new char[]{'a', 'b'}, get(GET_CHARACTERS));
        assertArrayEquals(BOOLEAN_VALUES, get(GET_BOOLEANS));
        assertArrayEquals(SHORT_VALUES, get(GET_SHORTS));
        assertArrayEquals(new String[]{"one", "two"}, get(GET_PASSWORDS));
        assertEquals(TIME_UNIT_VALUE, get(GET_TIME_UNIT));
        assertArrayEquals(TIME_UNIT_VALUES, get(GET_TIME_UNITS));
    }

    @Test
    public void build() throws Exception {
        final Object config = factory.create((Class) cl.loadClass(TestConfigurationAsAnnotation.class.getName()), props).build();
        assertEquals(STRING_VALUE, get(GET_STRING));
        assertEquals(LONG_VALUE, get(GET_LONG));
        assertEquals(DOUBLE_VALUE, get(GET_DOUBLE));
        assertEquals(FLOAT_VALUE, get(GET_FLOAT));
        assertEquals(INTEGER_VALUE, get(GET_INTEGER));
        assertEquals(BYTE_VALUE, get(GET_BYTE));
        assertEquals(CHARACTER_VALUE, get(GET_CHARACTER));
        assertEquals(BOOLEAN_VALUE, get(GET_BOOLEAN));
        assertEquals(SHORT_VALUE, get(GET_SHORT));
        assertEquals(PASSWORD_VALUE, get(GET_PASSWORD));
        assertArrayEquals(STRING_VALUES, get(GET_STRINGS));
        assertArrayEquals(LONG_VALUES, get(GET_LONGS));
        assertArrayEquals(DOUBLE_VALUES, get(GET_DOUBLES), 0);
        assertArrayEquals(FLOAT_VALUES, get(GET_FLOATS), 0);
        assertArrayEquals(INTEGER_VALUES, get(GET_INTEGERS));
        assertArrayEquals(BYTE_VALUES, get(GET_BYTES));
        assertArrayEquals(CHARACTER_VALUES, get(GET_CHARACTERS));
        assertArrayEquals(BOOLEAN_VALUES, get(GET_BOOLEANS));
        assertArrayEquals(SHORT_VALUES, get(GET_SHORTS));
        assertArrayEquals(PASSWORD_VALUES, get(GET_PASSWORDS));
        assertEquals(TIME_UNIT_VALUE, get(GET_TIME_UNIT));
        assertArrayEquals(TIME_UNIT_VALUES, get(GET_TIME_UNITS));
        assertArrayEquals(PASSWORD_VALUES, get(GET_PASSWORDS));
    }
}

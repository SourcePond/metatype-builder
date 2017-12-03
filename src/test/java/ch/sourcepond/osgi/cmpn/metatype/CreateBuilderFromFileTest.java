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
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Hashtable;

import static ch.sourcepond.osgi.cmpn.metatype.MTPBuilder.create;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.osgi.service.metatype.ObjectClassDefinition.ALL;

public class CreateBuilderFromFileTest {
    private final Bundle bundle = mock(Bundle.class);
    private final Hashtable<String, String> headers = new Hashtable<>();
    private MTPBuilder mtpBuilder;
    private OCDBuilder ocdBuilder;

    @Before
    public void setup() throws Exception {
        when(bundle.getHeaders()).thenReturn(headers);
        mtpBuilder = create((Class<? extends Annotation>) new TestClassLoader(bundle).loadClass(
                TestConfigurationAsAnnotation.class.getName()));
        ocdBuilder = mtpBuilder.getOCD().get(0);
        ocdBuilder.add();
    }

    private AttributeDefinition findAttribute(final AttributeDefinition[] pAll, final String pId) {
        for (int i = 0; i < pAll.length; i++) {
            if (pId.equals(pAll[i].getID())) {
                return pAll[i];
            }
        }
        throw new AssertionError(format("No attribute found for id '%s'", pId));
    }

    private AttributeDefinition assertBaseAttribute(final AttributeDefinition[] pAll,
                                                    final String pId,
                                                    final int pCardinality,
                                                    final Type pType,
                                                    final String pName,
                                                    final String pDescription,
                                                    final String[] pDefault) {
        final AttributeDefinition ad = findAttribute(pAll, pId);
        assertEquals(pId, ad.getID());
        assertEquals(pType.getValue(), ad.getType());
        assertEquals(pName, ad.getName());
        assertEquals(pDescription, ad.getDescription());
        assertEquals(pCardinality, ad.getCardinality());

        if (pDefault == null) {
            assertNull(ad.getDefaultValue());
        } else {
            assertArrayEquals(pDefault, ad.getDefaultValue());
        }
        return ad;
    }


    private void assertAttribute(final AttributeDefinition[] pAll,
                                 final String pId,
                                 final int pCardinality,
                                 final Type pType,
                                 final String pName,
                                 final String pDescription,
                                 final String[] pDefault) {
        final AttributeDefinition ad = assertBaseAttribute(pAll, pId, pCardinality, pType, pName, pDescription, pDefault);
        assertNull(ad.getOptionLabels());
        assertNull(ad.getOptionValues());
    }

    private void assertOptionAttribute(final AttributeDefinition[] pAll,
                                       final String pId,
                                       final int pCardinality,
                                       final Type pType,
                                       final String pName,
                                       final String pDescription,
                                       final String[] pOptionLabel,
                                       final String[] pOptionValue,
                                       final String[] pDefault) {
        final AttributeDefinition ad = assertBaseAttribute(pAll, pId, pCardinality, pType, pName, pDescription, pDefault);
        assertArrayEquals(pOptionLabel, ad.getOptionLabels());
        assertArrayEquals(pOptionValue, ad.getOptionValues());
    }

    private void assertIcon(final ObjectClassDefinition pOcd, final int pSize) throws IOException {
        final String expectedIconPath = format("/OSGI-INF/resources/icon%d.png", pSize);
        final byte[] expected = toByteArray(getClass().getResource(expectedIconPath));
        final byte[] actual = toByteArray(pOcd.getIcon(pSize));
        assertArrayEquals(expected, actual);
    }

    @Test
    public void verifyUnmodifiedBuilder() throws IOException {
        assertEquals(format("OSGI-INF/l10n/%s", TestConfigurationAsAnnotation.class.getName()), mtpBuilder.getLocalization());
        final MetaTypeProvider provider = mtpBuilder.build();
        final ObjectClassDefinition ocd = provider.getObjectClassDefinition(TestConfigurationAsAnnotation.class.getName(), null);
        assertNotNull(ocd);
        assertEquals("Test configuration", ocd.getDescription());
        assertEquals("TestConfigurationAsAnnotation", ocd.getName());
        assertEquals("ch.sourcepond.osgi.cmpn.metatype.TestConfigurationAsAnnotation", ocd.getID());
        assertIcon(ocd, 16);
        assertIcon(ocd, 32);

        final AttributeDefinition[] attrs = ocd.getAttributeDefinitions(ALL);
        assertEquals(22, attrs.length);

        assertAttribute(attrs, "getString", 0, Type.String, "Get string", "Some string", new String[]{"one"});
        assertAttribute(attrs, "getLong", 0, Type.Long, "Get long", "Some long", new String[]{"1"});
        assertAttribute(attrs, "getDouble", 0, Type.Double, "Get double", "Some double", new String[]{"1"});
        assertAttribute(attrs, "getFloat", 0, Type.Float, "Get float", "Some float", new String[]{"1"});
        assertAttribute(attrs, "getInteger", 0, Type.Integer, "Get integer", "Some integer", new String[]{"1"});
        assertAttribute(attrs, "getByte", 0, Type.Byte, "Get byte", "Some byte", new String[]{"1"});
        assertAttribute(attrs, "getCharacter", 0, Type.Char, "Get character", "Some character", new String[]{"a"});
        assertAttribute(attrs, "getBoolean", 0, Type.Boolean, "Get boolean", "Some boolean", new String[]{"true"});
        assertAttribute(attrs, "getShort", 0, Type.Short, "Get short", "Some short", new String[]{"1"});
        assertAttribute(attrs, "getPassword", 0, Type.Password, "Get password", "Some password", null);

        assertAttribute(attrs, "getStrings", 5, Type.String, "Get strings", "Some strings", new String[]{"one", "two"});
        assertAttribute(attrs, "getLongs", 5, Type.Long, "Get longs", "Some longs", new String[]{"1", "2"});
        assertAttribute(attrs, "getDoubles", 5, Type.Double, "Get doubles", "Some doubles", new String[]{"1", "2"});
        assertAttribute(attrs, "getFloats", 5, Type.Float, "Get floats", "Some floats", new String[]{"1", "2"});
        assertAttribute(attrs, "getIntegers", 5, Type.Integer, "Get integers", "Some integers", new String[]{"1", "2"});
        assertAttribute(attrs, "getBytes", 5, Type.Byte, "Get bytes", "Some bytes", new String[]{"1", "2"});
        assertAttribute(attrs, "getCharacters", 5, Type.Char, "Get characters", "Some characters", new String[]{"a", "b"});
        assertAttribute(attrs, "getBooleans", 5, Type.Boolean, "Get booleans", "Some booleans", new String[]{"true", "true"});
        assertAttribute(attrs, "getShorts", 5, Type.Short, "Get shorts", "Some shorts", new String[]{"1", "2"});
        assertAttribute(attrs, "getPasswords", 5, Type.Password, "Get passwords", "Some passwords", null);

        assertOptionAttribute(attrs, "getTimeUnit", 0, Type.String, "Get time unit", "Some time unit",
                new String[]{NANOSECONDS.name(), MICROSECONDS.name(), MILLISECONDS.name(), SECONDS.name(), MINUTES.name(), HOURS.name(), DAYS.name()},
                new String[]{NANOSECONDS.name(), MICROSECONDS.name(), MILLISECONDS.name(), SECONDS.name(), MINUTES.name(), HOURS.name(), DAYS.name()},
                new String[]{DAYS.name()});
        assertOptionAttribute(attrs, "getTimeUnits", 5, Type.String, "Get time units", "Some time units",
                new String[]{NANOSECONDS.name(), MICROSECONDS.name(), MILLISECONDS.name(), SECONDS.name(), MINUTES.name(), HOURS.name(), DAYS.name()},
                new String[]{NANOSECONDS.name(), MICROSECONDS.name(), MILLISECONDS.name(), SECONDS.name(), MINUTES.name(), HOURS.name(), DAYS.name()},
                new String[]{DAYS.name(), HOURS.name()});
    }
}

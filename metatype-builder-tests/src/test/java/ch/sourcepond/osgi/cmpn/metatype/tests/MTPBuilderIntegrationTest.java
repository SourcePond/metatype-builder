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
package ch.sourcepond.osgi.cmpn.metatype.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.service.metatype.ObjectClassDefinition;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

@RunWith(PaxExam.class)
public class MTPBuilderIntegrationTest {

    @Inject
    private BundleContext context;

    @Inject
    private MetaTypeService metaTypeService;

    private Bundle testBundle;

    @Configuration
    public Option[] configure() {
        return new Option[]{
                junitBundles(),
                mavenBundle("ch.sourcepond.osgi.cmpn", "metatype-builder-lib").version("0.1-SNAPSHOT"),
                mavenBundle("ch.sourcepond.osgi.cmpn", "metatype-builder-testbundle").version("0.1-SNAPSHOT"),
                mavenBundle("org.apache.felix", "org.apache.felix.metatype").version("1.1.6")
        };
    }

    @Before
    public void setup() {
        for (final Bundle b : context.getBundles()) {
            if (b.getSymbolicName().equals("ch.sourcepond.osgi.cmpn.metatype-builder-testbundle")) {
                testBundle = b;
                break;
            }
        }
        assertNotNull(testBundle);
    }

    @Test
    public void verifyBasicOCD() {
        final MetaTypeInformation info = metaTypeService.getMetaTypeInformation(testBundle);
        final ObjectClassDefinition def = info.getObjectClassDefinition("someId.pid", null);
        final AttributeDefinition[] attrs = def.getAttributeDefinitions(ObjectClassDefinition.ALL);
        assertEquals(1, attrs.length);
        assertEquals(AttributeDefinition.STRING, attrs[0].getType());
        assertEquals("someString", attrs[0].getID());
    }

    @Test
    public void verifyLocales() {
        final MetaTypeInformation info = metaTypeService.getMetaTypeInformation(testBundle);
        final ObjectClassDefinition def = info.getObjectClassDefinition("someId.pid", null);
        final String[] locales = info.getLocales();
        assertEquals(3, locales.length);
        assertEquals("de", locales[0]);
        assertEquals("de_CH_1024", locales[1]);
        assertEquals("de_CH", locales[2]);
    }
}

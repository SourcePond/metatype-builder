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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;

import javax.inject.Inject;

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
                mavenBundle("ch.sourcepond.osgi.cmpn", "metatype-builder").version("0.1-SNAPSHOT"),
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
    public void testIt() {
        final MetaTypeInformation info = metaTypeService.getMetaTypeInformation(testBundle);
        info.getLocales();
    }
}

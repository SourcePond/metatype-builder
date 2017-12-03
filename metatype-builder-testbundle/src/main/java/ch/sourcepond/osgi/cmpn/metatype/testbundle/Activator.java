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
package ch.sourcepond.osgi.cmpn.metatype.testbundle;

import ch.sourcepond.osgi.cmpn.metatype.MTPBuilder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.MetaTypeProvider;

import java.util.Hashtable;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final MTPBuilder builder = MTPBuilder.create(context.getBundle());
        final MetaTypeProvider provider = builder.
                ocd("someId.pid", "someName").
                stringAD("someString").add().
                add().build();

        final Hashtable regProps = new Hashtable<>();
        regProps.put(MetaTypeProvider.METATYPE_PID, "someId.pid");

        context.registerService(MetaTypeProvider.class, provider, regProps);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }
}

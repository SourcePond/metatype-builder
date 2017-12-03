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

import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

import java.io.IOException;

import static java.lang.String.format;

public class TestClassLoader extends ClassLoader implements BundleReference {
    private final Bundle bundle;

    public TestClassLoader(final Bundle pBundle) {
        bundle = pBundle;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (TestConfigurationAsAnnotation.class.getName().equals(name)) {
            final byte[] data;
            try {
                data = IOUtils.toByteArray(TestConfigurationAsAnnotation.class.getResource(format("/%s.class", name.replace('.', '/'))));
            } catch (IOException e) {
                throw new ClassNotFoundException(e.getMessage(), e);
            }
            return defineClass(name, data, 0, data.length);
        }
        return super.loadClass(name, resolve);
    }
}

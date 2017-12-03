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

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

public class DefaultValueAdapter extends XmlAdapter<String, List<String>> {

    @Override
    public List<String> unmarshal(final String v) throws Exception {
        final String[] elements = v.split(",");
        final List<String> defaults = new ArrayList<>(elements.length);
        for (final String element : elements) {
            defaults.add(element);
        }
        return defaults;
    }

    @Override
    public String marshal(final List<String> v) throws Exception {
        final StringBuilder builder = new StringBuilder();
        v.forEach(s -> builder.append(s).append(','));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}

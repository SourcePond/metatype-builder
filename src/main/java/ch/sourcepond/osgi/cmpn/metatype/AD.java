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

import org.osgi.service.metatype.AttributeDefinition;

import java.util.List;
import java.util.function.Function;

final class AD implements AttributeDefinition {
    private String id;
    private int type;
    private Function<String, ?> converter;
    private int cardinality;
    private String name;
    private String description;
    private List<Option> options;
    private List<String> defaultValue;
    private String min;
    private String max;

    public AD(final String pId,
              final int pType,
              final Function<String, ?> pConverter,
              final int pCardinality,
              final String pName,
              final String pDescription,
              final List<Option> pOptions,
              final List<String> pDefaultValue,
              final String pMin,
              final String pMax) {
        id = pId;
        type = pType;
        converter = pConverter;
        cardinality = pCardinality;
        name = pName;
        description = pDescription;
        options = pOptions;
        defaultValue = pDefaultValue;
        min = pMin;
        max = pMax;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCardinality() {
        return cardinality;
    }

    @Override
    public int getType() {
        return type;
    }

    private String[] getOptionEntries(final Function<Option, String> pConverter) {
        final String[] entries = new String[options.size()];
        if (entries.length > 0) {
            for (int i = 0; i < entries.length; i++) {
                entries[i] = pConverter.apply(options.get(i));
            }
        }
        return entries;
    }

    @Override
    public String[] getOptionValues() {
        return getOptionEntries(o -> o.getValue());
    }

    @Override
    public String[] getOptionLabels() {
        return getOptionEntries(o -> o.getLabel());
    }

    @Override
    public String[] getDefaultValue() {
        return defaultValue.toArray(new String[defaultValue.size()]);
    }

    @Override
    public String validate(final String value) {
        try {
            // TODO: Apply min/max here
            converter.apply(value);
        } catch (final Exception e) {
            return e.getLocalizedMessage();
        }
        return "";
    }
}

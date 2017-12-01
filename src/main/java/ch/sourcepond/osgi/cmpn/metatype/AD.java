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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

final class AD implements AttributeDefinition, Localizable<AD> {
    private final String id;
    private final int type;
    private final Function<String, ?> converter;
    private final int cardinality;
    private final String name;
    private final String description;
    private final List<Option> options;
    private final List<String> defaultValue;
    private final String min;
    private final String max;
    private final boolean required;

    public AD(final String pId,
              final int pType,
              final Function<String, ?> pConverter,
              final int pCardinality,
              final String pName,
              final String pDescription,
              final List<Option> pOptions,
              final List<String> pDefaultValue,
              final String pMin,
              final String pMax,
              final boolean pRequired) {
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
        required = pRequired;
    }

    @Override
    public AD cloneLocalized(final Localizer pLocalizer) {
        return new AD(id,
                type,
                converter,
                cardinality,
                pLocalizer.localize(name),
                pLocalizer.localize(description),
                options == null ? null : options.stream().map(opt -> opt.cloneLocalized(pLocalizer)).collect(toList()),
                defaultValue,
                min,
                max,
                required);
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
        if (options == null) {
            return null;
        }
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
        return defaultValue == null ? null : defaultValue.toArray(new String[defaultValue.size()]);
    }

    boolean isRequired() {
        return required;
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

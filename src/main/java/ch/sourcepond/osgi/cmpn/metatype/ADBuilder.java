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

import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Arrays.asList;

public abstract class ADBuilder<T> {
    private Option[] options;
    private final OCDBuilder ocdBuilder;
    private final String id;
    private final int type;
    private final Function<String, T> converter;
    private int cardinality;
    private String name;
    private String description;
    private String defaultValue[];
    private String max;
    private String min;
    private boolean required;

    public ADBuilder(final OCDBuilder pOCDBuilder,
                     final String pId,
                     final int pType,
                     final Function<String, T> pConverter) {
        ocdBuilder = pOCDBuilder;
        id = pId;
        type = pType;
        converter = pConverter;
    }

    public ADBuilder<T> setMax(final T pMax) {
        max = pMax.toString();
        return this;
    }

    public ADBuilder<T> setMin(final T pMin) {
        min = pMin.toString();
        return this;
    }

    public ADBuilder<T> setCardinality(final int pCardinality) {
        cardinality = pCardinality;
        return this;
    }

    public ADBuilder<T> setName(final String pName) {
        name = pName;
        return this;
    }

    public ADBuilder<T> setDescription(final String pDescription) {
        description = pDescription;
        return this;
    }

    private <E> void applySetter(final E[] pValues, final BiFunction<Option, E, Object> pSetter) {
        for (int i = 0 ; i < pValues.length ; i++) {
            options[i] = new Option();
            pSetter.apply(options[i], pValues[i]);
        }
    }

    private <E> void initOptions(final E[] pValues, final BiFunction<Option, E, Object> pSetter) {
        if (options == null) {
            options = new Option[pValues.length];
            applySetter(pValues, pSetter);
        } else {
            if (options.length != pValues.length) {
                throw new IllegalArgumentException(String.format("Invalid value array: size of options = %d, size of values %d", options.length, pValues.length));
            }
            applySetter(pValues, pSetter);
        }
    }

    public ADBuilder<T> setOptionValues(final T... pOptionValues) {
        initOptions(pOptionValues, (option, value)  -> {
            option.setValue(value.toString());
            return null;
        });
        return this;
    }

    public ADBuilder<T> setOptionLabels(final String... pOptionLabels) {
        initOptions(pOptionLabels, (option, value)  -> {
            option.setLabel(value.toString());
            return null;
        });
        return this;
    }

    public ADBuilder<T> setDefaultValue(final T... pDefaultValue) {
        defaultValue = new String[pDefaultValue.length];
        for (int i = 0; i < pDefaultValue.length; i++) {
            defaultValue[i] = pDefaultValue[i].toString();
        }
        return this;
    }

    public ADBuilder<T> setRequired(final boolean pRequired) {
        required = pRequired;
        return this;
    }

    public void add() {
        final AD<T> ad = new AD<>();
        ad.setCardinality(cardinality);
        ad.setConverter(converter);
        ad.setDefaultValue(defaultValue);
        ad.setDescription(description);
        ad.setId(id);
        ad.setMax(max);
        ad.setMin(min);
        ad.setName(name);
        ad.setRequired(required);
        ad.setType(type);

        if (options != null) {
            ad.getOption().addAll(asList(options));
        }

        ocdBuilder.addAttributeDefinition(ad);
    }
}

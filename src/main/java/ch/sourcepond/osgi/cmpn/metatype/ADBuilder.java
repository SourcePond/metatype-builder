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

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static ch.sourcepond.osgi.cmpn.metatype.Type.valueOf;
import static java.util.Objects.requireNonNull;

public class ADBuilder<T> {
    private final OCDBuilder ocdBuilder;
    private final Function<String, T> converter;
    private List<Option> options;
    private List<String> defaultValues;
    private String id;
    private String type;
    private int cardinality;
    private String name;
    private String description;
    private String defaultValue;
    private String max;
    private String min;

    public ADBuilder(final OCDBuilder pOCDBuilder,
                     final Function<String, T> pConverter) {
        ocdBuilder = pOCDBuilder;
        converter = pConverter;
    }

    @XmlElement(required = true)
    String getId() {
        return id;
    }

    @XmlElement(required = true)
    String getType() {
        return type;
    }

    @XmlElement
    int getCardinality() {
        return cardinality;
    }

    @XmlElement
    String getName() {
        return name;
    }

    @XmlElement
    String getDescription() {
        return description;
    }

    @XmlElement
    String getDefaultValue() {
        return defaultValue;
    }

    @XmlElement
    String getMin() {
        return min;
    }

    @XmlElement
    String getMax() {
        return max;
    }

    @XmlElement
    List<Option> getOption() {
        if (options == null) {
            options = new LinkedList<>();
        }
        return options;
    }

    private List<String> getDefaultValues() {
        if (defaultValues == null) {
            defaultValues = new LinkedList<>();
        }
        return defaultValues;
    }

    ADBuilder<T> id(final String pId) {
        id = pId;
        return this;
    }

    public ADBuilder<T> max(final T pMax) {
        max = pMax.toString();
        return this;
    }

    public ADBuilder<T> min(final T pMin) {
        min = pMin.toString();
        return this;
    }

    public ADBuilder<T> cardinality(final int pCardinality) {
        cardinality = pCardinality;
        return this;
    }

    public ADBuilder<T> name(final String pName) {
        name = pName;
        return this;
    }

    public ADBuilder<T> description(final String pDescription) {
        description = pDescription;
        return this;
    }

    public ADBuilder<T> option(final String pLabel, String pValue) {
        final Option opt = new Option();
        opt.setLabel(pLabel);
        opt.setValue(pValue);
        options.add(opt);
        return this;
    }

    public ADBuilder<T> defaultValue(final T... pDefaultValue) {
        final List<String> defaultValues = getDefaultValues();
        for (int i = 0; i < pDefaultValue.length; i++) {
            defaultValues.add(pDefaultValue[i].toString());
        }
        return this;
    }

    void setId(final String pId) {
        id = pId;
    }

    void setType(final String pType) {
        type = pType;
    }

    void setCardinality(final int pCardinality) {
        cardinality = pCardinality;
    }

    void setName(final String pName) {
        name = pName;
    }

    void setDescription(final String pDescription) {
        description = pDescription;
    }

    void setDefaultValue(final String pDefaultValue) {
        defaultValue = pDefaultValue;
    }

    void setMax(final String pMax) {
        max = pMax;
    }

    void setMin(final String pMin) {
        min = pMin;
    }

    public OCDBuilder add() {
        ocdBuilder.addAD(new AD<>(
                requireNonNull(id, "ID has not been set"),
                valueOf(requireNonNull(type, "Type has not been set")).getValue(),
                converter,
                cardinality,
                name,
                description,
                options == null ? Collections.emptyList() : options,
                defaultValues,
                min,
                max));
        return ocdBuilder;
    }
}

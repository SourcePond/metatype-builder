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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class ADBuilder<T> {
    private OCDBuilder ocdBuilder;
    private List<OptionBuilder> optionBuilders;
    private List<String> defaultValues;
    private String id;
    private Type type;
    private int cardinality;
    private String name;
    private String description;
    private String max;
    private String min;

    ADBuilder() {
    }

    ADBuilder<T> init(final OCDBuilder pOCDBuilder) {
        ocdBuilder = pOCDBuilder;
        return this;
    }

    @XmlAttribute(required = true)
    String getId() {
        return id;
    }

    @XmlJavaTypeAdapter(TypeAdapter.class)
    @XmlAttribute(required = true)
    Type getType() {
        return type;
    }

    @XmlAttribute
    int getCardinality() {
        return cardinality;
    }

    @XmlAttribute
    String getName() {
        return name;
    }

    @XmlAttribute
    String getDescription() {
        return description;
    }

    @XmlAttribute
    List<String> getDefault() {
        if (defaultValues == null) {
            defaultValues = new LinkedList<>();
        }
        return defaultValues;
    }

    @XmlAttribute
    String getMin() {
        return min;
    }

    @XmlAttribute
    String getMax() {
        return max;
    }

    @XmlElement
    List<OptionBuilder> getOption() {
        if (optionBuilders == null) {
            optionBuilders = new LinkedList<>();
        }
        return optionBuilders;
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

    ADBuilder<T> type(final Type pType) {
        type = pType;
        return this;
    }

    public OptionBuilder<T> option() {
        return new OptionBuilder<T>().init(this);
    }

    public ADBuilder<T> defaultValue(final T... pDefaultValue) {
        final List<String> defaultValues = getDefault();
        for (int i = 0; i < pDefaultValue.length; i++) {
            defaultValues.add(pDefaultValue[i].toString());
        }
        return this;
    }

    void setId(final String pId) {
        id = pId;
    }

    void setType(final Type pType) {
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

    void setMax(final String pMax) {
        max = pMax;
    }

    void setMin(final String pMin) {
        min = pMin;
    }

    AD build() {
        final List<Option> options;
        if (optionBuilders == null) {
            options = emptyList();
        } else {
            options = optionBuilders.stream().map(builder -> builder.build()).collect(toList());
        }

        return new AD(
                requireNonNull(id, "ID has not been set"),
                requireNonNull(type, "Type has not been set").getValue(),
                type.getConverter(),
                cardinality,
                name,
                description,
                options,
                defaultValues,
                min,
                max);
    }

    public OCDBuilder add() {
        ocdBuilder.addAD(this);
        return ocdBuilder;
    }
}

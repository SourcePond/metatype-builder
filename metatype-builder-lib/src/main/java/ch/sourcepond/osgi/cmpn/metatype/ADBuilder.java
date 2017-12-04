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

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class ADBuilder<T> {
    private OCDBuilder parent;
    private List<OptionBuilder<T>> optionBuilders;
    private List<String> defaultValues;
    private String id;
    private Type type;
    private int cardinality;
    private String name;
    private String description;
    private String max;
    private String min;

    // Default is true, see osgi.cmpn-6.0.0.pdf / 105.8 Meta Type Resource XML Schema
    private boolean required = true;

    ADBuilder() {
    }

    void initAfterUnmarshal(final OCDBuilder pParent) {
        setParent(pParent);
        if (optionBuilders != null) {
            optionBuilders.forEach(optionBuilder -> optionBuilder.setParent(this));
        }
    }

    ADBuilder<T> setParent(final OCDBuilder pOCDBuilder) {
        parent = pOCDBuilder;
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

    @XmlJavaTypeAdapter(DefaultValueAdapter.class)
    @XmlAttribute
    List<String> getDefault() {
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

    @XmlAttribute
    boolean isRequired() {
        return required;
    }

    @XmlElement(name = "Option")
    List<OptionBuilder<T>> getOption() {
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
        return new OptionBuilder<T>().setParent(this);
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

    void setRequired(final boolean pRequired) {
        required = pRequired;
    }

    void setDefault(final List<String> pDefaultValues) {
        defaultValues = pDefaultValues;
    }

    AD build() {
        return new AD(
                requireNonNull(id, "ID has not been set"),
                requireNonNull(type, "Type has not been set").getValue(),
                type.getConverter(),
                cardinality,
                name,
                description,
                optionBuilders == null ? null : optionBuilders.stream().map(builder -> builder.build()).collect(toList()),
                defaultValues,
                min,
                max,
                required);
    }

    public OCDBuilder add() {
        parent.addAD(this);
        return parent;
    }
}

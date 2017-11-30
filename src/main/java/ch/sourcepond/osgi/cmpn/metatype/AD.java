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

import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

final class AD<T> implements AttributeDefinition {
    private String id;
    private int type;
    private Function<String, T> converter;
    private int cardinality;
    private String name;
    private String description;
    private String rawDefaultValue;
    private List<Option> option;
    private String[] defaultValue;
    private String min;
    private String max;
    private boolean required;

    @XmlElement
    public boolean isRequired() {
        return required;
    }

    @XmlElement(name = "id", required = true)
    @Override
    public String getID() {
        return id;
    }

    @XmlElement(required = true)
    @Override
    public int getType() {
        return type;
    }

    @XmlElement
    @Override
    public int getCardinality() {
        return cardinality;
    }

    @XmlElement
    @Override
    public String getName() {
        return name;
    }

    @XmlElement
    @Override
    public String getDescription() {
        return description;
    }

    private String[] getOptionEntries(final Function<Option, String> pConverter) {
        final List<Option> options = getOption();
        final String[] entries = new String[getOption().size()];
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
        return defaultValue;
    }

    @XmlElement(name = "default")
    public String getRawDefaultValue() {
        return rawDefaultValue;
    }

    @XmlElement
    public String getMin() {
        return min;
    }

    @XmlElement
    public String getMax() {
        return max;
    }

    @XmlElement
    public List<Option> getOption() {
        if (option == null) {
            option = new LinkedList<>();
        }
        return option;
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

    public void setRequired(final boolean pRequired) {
        required = pRequired;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setConverter(Function<String, T> converter) {
        this.converter = converter;
    }

    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRawDefaultValue(final String pRawDefaultValue) {
        rawDefaultValue = pRawDefaultValue;
        defaultValue = pRawDefaultValue.split(",");
    }

    public void setDefaultValue(String[] defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }
}

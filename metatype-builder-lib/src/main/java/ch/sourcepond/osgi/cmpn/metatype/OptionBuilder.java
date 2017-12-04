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

import static java.util.Objects.requireNonNull;

public class OptionBuilder<T, U> {
    private ADBuilder<T, U> parent;
    private String label;
    private String value;

    OptionBuilder<T, U> setParent(final ADBuilder<T, U> pParent) {
        parent = pParent;
        return this;
    }

    @XmlAttribute(required = true)
    String getLabel() {
        return label;
    }

    @XmlAttribute(required = true)
    String getValue() {
        return value;
    }

    public OptionBuilder label(final String pLabel) {
        setLabel(pLabel);
        return this;
    }

    public OptionBuilder value(final String pValue) {
        setValue(pValue);
        return this;
    }

    void setLabel(String pLabel) {
        label = requireNonNull(pLabel, "Label is null");
    }

    void setValue(String pValue) {
        value = requireNonNull(pValue, "Value is null");
    }

    Option build() {
        return new Option(label, value);
    }

    public ADBuilder<T, U> add() {
        requireNonNull(label, "Label is null");
        requireNonNull(value, "Value is null");
        parent.getOption().add(this);
        return parent;
    }
}

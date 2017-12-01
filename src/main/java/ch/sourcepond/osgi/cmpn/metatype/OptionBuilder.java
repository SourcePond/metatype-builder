package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import static java.util.Objects.requireNonNull;

public class OptionBuilder<T> {
    private ADBuilder<T> adBuilder;
    private String label;
    private String value;

    OptionBuilder<T> init(ADBuilder<T> pAdBuilder) {
        adBuilder = pAdBuilder;
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
        label = requireNonNull(pLabel, "Label is null");
        return this;
    }

    public OptionBuilder value(final String pValue) {
        value = requireNonNull(pValue, "Value is null");
        return this;
    }

    void setLabel(String pLabel) {
        label = pLabel;
    }

    void setValue(String pValue) {
        value = pValue;
    }

    Option build() {
        return new Option(label, value);
    }

    public ADBuilder<T> add() {
        adBuilder.getOption().add(this);
        return adBuilder;
    }
}

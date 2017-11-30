package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlElement;

class Option {
    private String label;
    private String value;

    @XmlElement
    public String getLabel() {
        return label;
    }

    @XmlElement
    public String getValue() {
        return value;
    }

    public void setLabel(String pLabel) {
        label = pLabel;
    }

    public void setValue(String pValue) {
        value = pValue;
    }
}

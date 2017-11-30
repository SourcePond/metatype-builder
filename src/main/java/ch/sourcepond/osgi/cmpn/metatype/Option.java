package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlElement;

class Option {
    private String label;
    private String value;

    @XmlElement(required = true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlElement(required = true)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

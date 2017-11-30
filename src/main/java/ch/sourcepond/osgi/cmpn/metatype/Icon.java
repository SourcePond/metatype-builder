package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlAttribute;

class Icon {
    private String resource;
    private int size;

    @XmlAttribute(required = true)
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @XmlAttribute(required = true)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

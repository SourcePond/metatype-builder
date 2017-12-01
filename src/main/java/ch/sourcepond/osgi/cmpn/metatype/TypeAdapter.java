package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class TypeAdapter extends XmlAdapter<String, Type> {

    @Override
    public Type unmarshal(final String v) throws Exception {
        return Type.valueOf(v);
    }

    @Override
    public String marshal(Type v) throws Exception {
        return v.name();
    }
}

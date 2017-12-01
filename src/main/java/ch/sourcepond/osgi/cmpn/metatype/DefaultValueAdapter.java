package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

public class DefaultValueAdapter extends XmlAdapter<String, List<String>> {

    @Override
    public List<String> unmarshal(final String v) throws Exception {
        final String[] elements = v.split(",");
        final List<String> defaults = new ArrayList<>(elements.length);
        for (final String element : elements) {
            defaults.add(element);
        }
        return defaults;
    }

    @Override
    public String marshal(final List<String> v) throws Exception {
        final StringBuilder builder = new StringBuilder();
        v.forEach(s -> builder.append(s).append(','));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}

package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@XmlRootElement
public class MetaData {
    private List<OCDBuilder> ocd = new LinkedList<>();

    @XmlElement
    public List<OCDBuilder> getOCD() {
        return ocd;
    }

    public <T extends Annotation> OCDBuilder get(final Class<T> pConfigDefinition) {
        final Optional<OCDBuilder> builder = ocd.stream().filter(b -> pConfigDefinition.getName().equals(b.getId())).findFirst();
        if (builder.isPresent()) {
            return builder.get();
        }
        throw new IllegalArgumentException(format("No OCD element found with id '%s'", pConfigDefinition.getName()));
    }
}

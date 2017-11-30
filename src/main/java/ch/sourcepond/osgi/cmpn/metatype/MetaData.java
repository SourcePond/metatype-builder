package ch.sourcepond.osgi.cmpn.metatype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class MetaData {
    private List<OCD> ocd = new LinkedList<>();

    @XmlElement
    public List<OCD> getOCD() {
        return ocd;
    }
}

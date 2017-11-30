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
import org.osgi.service.metatype.ObjectClassDefinition;
import org.slf4j.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@XmlRootElement
final class OCD implements ObjectClassDefinition {
    private static final Logger LOG = getLogger(OCD.class);
    private static final int REQUIRED_ATTR = 0;
    private static final AttributeDefinition[] TEMPLATE_ARRAY = new AttributeDefinition[0];
    private String name;
    private String id;
    private String description;
    private List<AttributeDefinition> attributeDefinitions;
    private List<Icon> icon;

    @XmlElement
    public List<AttributeDefinition> getAD() {
        if (attributeDefinitions == null) {
            attributeDefinitions = new LinkedList<>();
        }
        return attributeDefinitions;
    }

    @XmlAttribute(required = true)
    @Override
    public String getName() {
        return name;
    }

    @XmlAttribute(name = "id", required = true)
    @Override
    public String getID() {
        return id;
    }

    @XmlAttribute
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public AttributeDefinition[] getAttributeDefinitions(int filter) {
        final AttributeDefinition[] ads;
        switch (filter) {
            case ALL: {
                ads = getAD().toArray(TEMPLATE_ARRAY);
                break;
            }
            case REQUIRED: {
                ads = getAD().stream().filter(ad -> ad.getCardinality() == REQUIRED_ATTR).
                        collect(Collectors.toList()).toArray(TEMPLATE_ARRAY);
                break;
            }
            case OPTIONAL: {
                ads = getAD().stream().filter(ad -> ad.getCardinality() != REQUIRED_ATTR).
                        collect(Collectors.toList()).toArray(TEMPLATE_ARRAY);
                break;
            }
            default: {
                ads = null;
            }
        }
        return ads;
    }

    @XmlElement(name = "Icon")
    public List<Icon> getIcon() {
        if (icon == null) {
            icon = new LinkedList<>();
        }
        return icon;
    }

    @Override
    public InputStream getIcon(int size) throws IOException {
        final Optional<Icon> opt = getIcon().stream().filter(i -> size == i.getSize()).findFirst();
        try {
            return opt.isPresent() ? new URI(opt.get().getResource()).toURL().openStream() : null;
        } catch (final URISyntaxException e) {
            LOG.warn(e.getMessage(), e);
            return null;
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}

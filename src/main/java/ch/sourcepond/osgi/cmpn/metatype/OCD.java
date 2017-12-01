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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

final class OCD implements ObjectClassDefinition {
    private static final Logger LOG = getLogger(OCD.class);
    private static final int REQUIRED_ATTR = 0;
    private static final AttributeDefinition[] TEMPLATE_ARRAY = new AttributeDefinition[0];
    private final String name;
    private final String id;
    private final String description;
    private final List<AD> ad;
    private final List<Icon> icon;

    public OCD(final String pName,
               final String pId,
               final String pDescription,
               final List<AD> pAd,
               final List<Icon> pIcon) {
        name = pName;
        id = pId;
        description = pDescription;
        ad = pAd;
        icon = pIcon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public AttributeDefinition[] getAttributeDefinitions(int filter) {
        final AttributeDefinition[] ads;
        switch (filter) {
            case ALL: {
                ads = ad.toArray(TEMPLATE_ARRAY);
                break;
            }
            case REQUIRED: {
                ads = ad.stream().filter(ad -> ad.isRequired()).collect(Collectors.toList()).toArray(TEMPLATE_ARRAY);
                break;
            }
            case OPTIONAL: {
                ads = ad.stream().filter(ad -> !ad.isRequired()).collect(Collectors.toList()).toArray(TEMPLATE_ARRAY);
                break;
            }
            default: {
                ads = null;
            }
        }
        return ads;
    }

    @Override
    public InputStream getIcon(int size) throws IOException {
        final Optional<Icon> opt = icon.stream().filter(i -> size == i.getSize()).findFirst();
        try {
            return opt.isPresent() ? new URI(opt.get().getResource()).toURL().openStream() : null;
        } catch (final URISyntaxException e) {
            LOG.warn(e.getMessage(), e);
            return null;
        }
    }
}

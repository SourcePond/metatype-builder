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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

final class OCD implements ObjectClassDefinition, Localizable<OCD> {
    private static final AttributeDefinition[] TEMPLATE_ARRAY = new AttributeDefinition[0];
    private final String name;
    private final String id;
    private final String description;
    private final List<AD> ad;
    private final List<Icon> icon;

    public OCD(final String pId,
               final List<Icon> pIcon,
               final List<AD> pAd,
               final String pName,
               final String pDescription) {
        name = pName;
        id = pId;
        description = pDescription;
        ad = pAd;
        icon = pIcon;
    }

    @Override
    public OCD cloneLocalized(final Localizer pLocalizer) {
        return new OCD(id,
                icon,
                ad.stream().map(ad -> ad.cloneLocalized(pLocalizer)).collect(toList()),
                pLocalizer.localize(name),
                pLocalizer.localize(description));
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
                ads = ad.stream().filter(ad -> ad.isRequired()).collect(toList()).toArray(TEMPLATE_ARRAY);
                break;
            }
            case OPTIONAL: {
                ads = ad.stream().filter(ad -> !ad.isRequired()).collect(toList()).toArray(TEMPLATE_ARRAY);
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
        if (opt.isPresent()) {
            return new URL(getClass().getResource("/"), opt.get().getResource()).openStream();
        }
        return null;
    }
}

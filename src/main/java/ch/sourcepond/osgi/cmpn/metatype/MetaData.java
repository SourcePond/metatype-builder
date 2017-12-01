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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Locale.getDefault;

@XmlRootElement(namespace = "metatype")
class MetaData {
    private final List<OCDBuilder> ocdBuilders = new LinkedList<>();

    @XmlElement(name = "OCD")
    public List<OCDBuilder> getOCD() {
        return ocdBuilders;
    }

    public <T extends Annotation> OCDBuilder find(final MTPBuilder pMtpBuilder, final Class<T> pConfigDefinition) {
        final Optional<OCDBuilder> builder = ocdBuilders.stream().filter(b -> pConfigDefinition.getName().equals(b.getId())).findFirst();
        if (builder.isPresent()) {
            return builder.get().init(pMtpBuilder, getDefault().toString());
        }
        throw new IllegalArgumentException(format("No OCD builder found with id '%s'", pConfigDefinition.getName()));
    }
}

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

import javax.xml.bind.annotation.adapters.XmlAdapter;

class TypeAdapter extends XmlAdapter<String, Type<?, ?>> {

    @Override
    public Type unmarshal(final String v) throws Exception {
        return Type.valueOf(v);
    }

    @Override
    public String marshal(final Type<?, ?> v) throws Exception {
        return v.getName();
    }
}

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

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class OCDBuilder {
    private final List<AttributeDefinition> attributeDefinitions = new LinkedList<>();
    private final Map<Integer, URL> icons = new HashMap<>();
    private final MTPBuilder mtpBuilder;
    private final String locale;
    private String name;
    private String id;
    private String description;

    OCDBuilder(final MTPBuilder pMtpBuilder, final String pLocale) {
        locale = pLocale;
        mtpBuilder = pMtpBuilder;
    }

    public ADBuilder<Boolean> booleanAD(final String pId) {
        return new BooleanADBuilder(this, pId);
    }

    public ADBuilder<Byte> byteAD(final String pId) {
        return new ByteADBuilder(this, pId);
    }

    public ADBuilder<Character> charAD(final String pId) {
        return new CharacterADBuilder(this, pId);
    }

    public ADBuilder<Double> doubleAD(final String pId) {
        return new DoubleADBuilder(this, pId);
    }

    public ADBuilder<Float> floatAD(final String pId) {
        return new FloatADBuilder(this, pId);
    }

    public ADBuilder<Integer> intAD(final String pId) {
        return new IntegerADBuilder(this, pId);
    }

    public ADBuilder<Long> longAD(final String pId) {
        return new LongADBuilder(this, pId);
    }

    public ADBuilder<String> passwordAD(final String pId) {
        return new PasswordADBuilder(this, pId);
    }

    public ADBuilder<String> stringAD(final String pId) {
        return new StringADBuilder(this, pId);
    }

    public <T extends Enum<T>> ADBuilder<T> enumAD(final String pId, final Class<T> pEnumType) {
        return new EnumADBuilder<>(this, pId, pEnumType).setOptionValues(pEnumType.getEnumConstants());
    }

    void addAttributeDefinition(final AttributeDefinition pDefinition) {
        attributeDefinitions.add(pDefinition);
    }

    public OCDBuilder setName(final String pName) {
        name = pName;
        return this;
    }

    public OCDBuilder setId(final String pId) {
        id = pId;
        return this;
    }

    public OCDBuilder setDescription(final String pDescription) {
        description = pDescription;
        return this;
    }

    public OCDBuilder setIcon(final int pSize, final URL pResource) {
        icons.put(pSize, pResource);
        return this;
    }

    public void add() {
        final OCD ocd = new OCD();
        ocd.setId(requireNonNull(id, "No id set"));
        ocd.setName(requireNonNull(name, "No name set"));
        ocd.setDescription(description);
        ocd.getAD().addAll(attributeDefinitions);
        icons.entrySet().forEach(entry -> {
            final Icon icon = new Icon();
            icon.setSize(entry.getKey());
            icon.setResource(entry.getValue().toString());
            ocd.getIcon().add(icon);
        });
        mtpBuilder.addOCD(locale, ocd);
    }
}

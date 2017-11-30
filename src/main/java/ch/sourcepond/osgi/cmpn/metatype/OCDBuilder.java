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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

public class OCDBuilder {
    private final MTPBuilder mtpBuilder;
    private final String locale;
    private List<AttributeDefinition> ad;
    private List<Icon> icon;
    private String name;
    private String id;
    private String description;

    OCDBuilder(final MTPBuilder pMtpBuilder, final String pLocale) {
        locale = pLocale;
        mtpBuilder = pMtpBuilder;
    }

    public ADBuilder<Boolean> booleanAD(final String pId) {
        return new BooleanADBuilder(this).id(pId);
    }

    public ADBuilder<Byte> byteAD(final String pId) {
        return new ByteADBuilder(this).id(pId);
    }

    public ADBuilder<Character> charAD(final String pId) {
        return new CharacterADBuilder(this).id(pId);
    }

    public ADBuilder<Double> doubleAD(final String pId) {
        return new DoubleADBuilder(this).id(pId);
    }

    public ADBuilder<Float> floatAD(final String pId) {
        return new FloatADBuilder(this).id(pId);
    }

    public ADBuilder<Integer> intAD(final String pId) {
        return new IntegerADBuilder(this).id(pId);
    }

    public ADBuilder<Long> longAD(final String pId) {
        return new LongADBuilder(this).id(pId);
    }

    public ADBuilder<String> passwordAD(final String pId) {
        return new PasswordADBuilder(this).id(pId);
    }

    public ADBuilder<String> stringAD(final String pId) {
        return new StringADBuilder(this).id(pId);
    }

    public <T extends Enum<T>> ADBuilder<T> enumAD(final String pId, final Class<T> pEnumType) {
        final ADBuilder<T> builder = new EnumADBuilder<>(this, pEnumType).id(pId);
        for (final T e : pEnumType.getEnumConstants()) {
            builder.option(e.name(), e.name());
        }
        return builder;
    }

    void addAD(final AttributeDefinition pAd) {
        ad.add(pAd);
    }

    public OCDBuilder name(final String pName) {
        name = pName;
        return this;
    }

    public OCDBuilder description(final String pDescription) {
        description = pDescription;
        return this;
    }

    public OCDBuilder icon(final int pSize, final URL pResource) {
        final Icon ic = new Icon();
        ic.setSize(pSize);
        ic.setResource(pResource.toString());
        icon.add(ic);
        return this;
    }

    @XmlElement
    List<AttributeDefinition> getAD() {
        if (ad == null) {
            ad = new LinkedList<>();
        }
        return ad;
    }

    @XmlElement(name = "Icon")
    public List<Icon> getIcon() {
        if (icon == null) {
            icon = new LinkedList<>();
        }
        return icon;
    }

    @XmlAttribute(required = true)
    String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    String getId() {
        return id;
    }

    @XmlAttribute
    String getDescription() {
        return description;
    }

    public MTPBuilder add() {
        final OCD ocd = new OCD(name,
                id,
                description,
                ad == null ? emptyList() : ad,
                icon == null ? emptyList() : icon);
        mtpBuilder.addOCD(locale, ocd);
        return mtpBuilder;
    }
}

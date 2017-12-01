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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class OCDBuilder {
    private MTPBuilder mtpBuilder;
    private String locale;
    private List<ADBuilder<?>> adBuilders;
    private List<Icon> icon;
    private String name;
    private String id;
    private String description;

    OCDBuilder() {
    }

    OCDBuilder init(final MTPBuilder pMtpBuilder, final String pLocale) {
        mtpBuilder = pMtpBuilder;
        locale = pLocale;
        return this;
    }

    OCDBuilder init(final MTPBuilder pMtpBuilder, final String pLocale, final String pId, final String pName) {
        init(pMtpBuilder, pLocale);
        id = pId;
        name = pName;
        return this;
    }

    private static String stringValueOf(final String pValue) {
        return pValue;
    }

    public ADBuilder<Boolean> booleanAD(final String pId) {
        return new ADBuilder<Boolean>().init(this).id(pId).type(Type.Boolean);
    }

    public ADBuilder<Byte> byteAD(final String pId) {
        return new ADBuilder<Byte>().init(this).id(pId).type(Type.Byte);
    }

    public ADBuilder<Character> charAD(final String pId) {
        return new ADBuilder<Character>().init(this).id(pId).type(Type.Char);
    }

    public ADBuilder<Double> doubleAD(final String pId) {
        return new ADBuilder<Double>().init(this).id(pId).type(Type.Double);
    }

    public ADBuilder<Float> floatAD(final String pId) {
        return new ADBuilder<Float>().init(this).id(pId).type(Type.Float);
    }

    public ADBuilder<Integer> intAD(final String pId) {
        return new ADBuilder<Integer>().init(this).id(pId).type(Type.Integer);
    }

    public ADBuilder<Long> longAD(final String pId) {
        return new ADBuilder<Long>().init(this).id(pId).type(Type.Long);
    }

    public ADBuilder<String> passwordAD(final String pId) {
        return new ADBuilder<String>().init(this).id(pId).type(Type.String);
    }

    public ADBuilder<String> stringAD(final String pId) {
        return new ADBuilder<String>().init(this).id(pId).type(Type.Password);
    }

    public <T extends Enum<T>> ADBuilder<String> optionsAD(final String pId, final Class<T> pEnumType) {
        final ADBuilder<String> builder = new ADBuilder<String>().init(this).id(pId).type(Type.String);
        for (final T e : pEnumType.getEnumConstants()) {
            builder.option().label(e.name()).value(e.name()).add();
        }
        return builder;
    }

    void addAD(final ADBuilder<?> pAd) {
        adBuilders.add(pAd);
    }

    public OCDBuilder description(final String pDescription) {
        description = pDescription;
        return this;
    }

    public OCDBuilder icon(final int pSize, final String pResource) {
        final Icon ic = new Icon();
        ic.setSize(pSize);
        ic.setResource(pResource);
        icon.add(ic);
        return this;
    }

    @XmlElement(name = "AD")
    List<ADBuilder<?>> getAD() {
        if (adBuilders == null) {
            adBuilders = new LinkedList<>();
        }
        return adBuilders;
    }

    @XmlElement(name = "Icon")
    List<Icon> getIcon() {
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

    void setName(final String pName) {
        name = pName;
    }

    void setId(final String pId) {
        id = pId;
    }

    void setDescription(final String pDescription) {
        description = pDescription;
    }

    OCD build() {
        final List<AD> ad;
        if (adBuilders == null) {
            ad = emptyList();
        } else {
            ad = adBuilders.stream().map(b -> b.init(this).build()).collect(toList());

        }
        return new OCD(name,
                id,
                description,
                adBuilders == null ? emptyList() : ad,
                icon == null ? emptyList() : icon);
    }

    public MTPBuilder add() {
        mtpBuilder.addOCD(locale, this);
        return mtpBuilder;
    }
}

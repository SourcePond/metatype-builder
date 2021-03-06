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
    private transient MTPBuilder mtpBuilder;
    private transient ClassLoader sourceLoader;
    private List<ADBuilder<?, ?>> adBuilders;
    private List<IconBuilder> iconBuilders;
    private String name;
    private String id;
    private String description;

    OCDBuilder() {
    }

    void initAfterUnmarshal(final MTPBuilder pMtpBuilder, final ClassLoader pSourceLoader) {
        mtpBuilder = pMtpBuilder;
        sourceLoader = pSourceLoader;
        adBuilders.forEach(adBuilder -> adBuilder.initAfterUnmarshal(this));
    }

    OCDBuilder init(final MTPBuilder pMtpBuilder, final String pId, final String pName) {
        mtpBuilder = pMtpBuilder;
        id = pId;
        name = pName;
        return this;
    }

    public ADBuilder<Boolean, Boolean> booleanAD(final String pId) {
        return new ADBuilder<Boolean, Boolean>().setParent(this).id(pId).type(Type.BOOLEAN);
    }

    public ADBuilder<Byte, Byte> byteAD(final String pId) {
        return new ADBuilder<Byte, Byte>().setParent(this).id(pId).type(Type.BYTE);
    }

    public ADBuilder<Character, Character> charAD(final String pId) {
        return new ADBuilder<Character, Character>().setParent(this).id(pId).type(Type.CHARACTER);
    }

    public ADBuilder<Double, Double> doubleAD(final String pId) {
        return new ADBuilder<Double, Double>().setParent(this).id(pId).type(Type.DOUBLE);
    }

    public ADBuilder<Float, Float> floatAD(final String pId) {
        return new ADBuilder<Float, Float>().setParent(this).id(pId).type(Type.FLOAT);
    }

    public ADBuilder<Integer, Integer> intAD(final String pId) {
        return new ADBuilder<Integer, Integer>().setParent(this).id(pId).type(Type.INTEGER);
    }

    public ADBuilder<Long, Long> longAD(final String pId) {
        return new ADBuilder<Long, Long>().setParent(this).id(pId).type(Type.LONG);
    }

    public ADBuilder<String, Integer> passwordAD(final String pId) {
        return new ADBuilder<String, Integer>().setParent(this).id(pId).type(Type.PASSWORD);
    }

    public ADBuilder<String, Integer> stringAD(final String pId) {
        return new ADBuilder<String, Integer>().setParent(this).id(pId).type(Type.STRING);
    }

    public <T extends Enum<T>> ADBuilder<String, Integer> optionsAD(final String pId, final Class<T> pEnumType) {
        final ADBuilder<String, Integer> builder = new ADBuilder<String, Integer>().setParent(this).id(pId).type(Type.STRING);
        for (final T e : pEnumType.getEnumConstants()) {
            builder.option().label(e.name()).value(e.name()).add();
        }
        return builder;
    }

    void addAD(final ADBuilder<?, ?> pAd) {
        getAD().add(pAd);
    }

    public OCDBuilder description(final String pDescription) {
        description = pDescription;
        return this;
    }

    public IconBuilder icon() {
        return new IconBuilder().setParent(this);
    }

    @XmlElement(name = "AD")
    List<ADBuilder<?, ?>> getAD() {
        if (adBuilders == null) {
            adBuilders = new LinkedList<>();
        }
        return adBuilders;
    }

    @XmlElement(name = "Icon")
    List<IconBuilder> getIcon() {
        if (iconBuilders == null) {
            iconBuilders = new LinkedList<>();
        }
        return iconBuilders;
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
        return new OCD(id, iconBuilders == null ? emptyList() :
                iconBuilders.stream().map(i -> i.setParent(this).build()).collect(toList()),
                adBuilders == null ? emptyList() : adBuilders.stream().map(b -> b.setParent(this).build()).collect(toList()),
                name,
                description,
                sourceLoader
        );
    }

    public MTPBuilder add() {
        mtpBuilder.addOCD(this);
        return mtpBuilder;
    }
}

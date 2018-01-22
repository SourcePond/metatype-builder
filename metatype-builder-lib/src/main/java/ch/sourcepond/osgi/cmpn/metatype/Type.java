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

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.String.format;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Objects.requireNonNull;

final class Type<T, U> {
    static final Type<String, Integer> STRING = new Type<>("String",
            AttributeDefinition.STRING,
            Type::same,
            Type::length,
            Validators::validateMinLength,
            Validators::validateMaxLength);
    static final Type<Long, Long> LONG = new Type<Long, Long>("Long",
            AttributeDefinition.LONG,
            Long::valueOf,
            Long::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Double, Double> DOUBLE = new Type<>("Double",
            AttributeDefinition.DOUBLE,
            Double::valueOf,
            Double::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Float, Float> FLOAT = new Type<>("Float",
            AttributeDefinition.FLOAT,
            Float::valueOf,
            Float::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Integer, Integer> INTEGER = new Type<>("Integer",
            AttributeDefinition.INTEGER,
            Integer::valueOf,
            Integer::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Byte, Byte> BYTE = new Type<>("Byte",
            AttributeDefinition.BYTE,
            Byte::valueOf,
            Byte::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Character, Character> CHARACTER = new Type<>("Char",
            AttributeDefinition.CHARACTER,
            Type::charValueOf,
            Type::charValueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Boolean, Boolean> BOOLEAN = new Type<>("Boolean",
            AttributeDefinition.BOOLEAN,
            Boolean::valueOf,
            Boolean::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<Short, Short> SHORT = new Type<>("Short",
            AttributeDefinition.SHORT,
            Short::valueOf,
            Short::valueOf,
            Validators::validateMin,
            Validators::validateMax);
    static final Type<String, Integer> PASSWORD = new Type<>(
            "Password",
            AttributeDefinition.PASSWORD,
            Type::same,
            Type::length,
            Validators::validateMinLength,
            Validators::validateMaxLength);
    private static final Type<?, ?>[] TYPES;

    static {
        final List<Type<?, ?>> types = new LinkedList<>();
        for (final Field f : Type.class.getDeclaredFields()) {
            if (isStatic(f.getModifiers()) && Type.class.equals(f.getType())) {
                f.setAccessible(true);
                final Type<?, ?> type;
                try {
                    types.add((Type<?, ?>) f.get(Type.class));
                } catch (final IllegalAccessException e) {
                    // This should never happen
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
        }
        TYPES = types.toArray(new Type<?, ?>[0]);
    }

    private final String name;
    private final int value;
    private final Function<String, T> valueConverter;
    private final Function<String, U> validationConverter;
    private final BiFunction<T, U, String> minValidator;
    private final BiFunction<T, U, String> maxValidator;

    private Type(final String pName,
                 final int pValue,
                 final Function<String, T> pValueConverter,
                 final Function<String, U> pValidationConverter,
                 final BiFunction<T, U, String> pMinValidator,
                 final BiFunction<T, U, String> pMaxValidator) {
        name = pName;
        value = pValue;
        valueConverter = pValueConverter;
        validationConverter = pValidationConverter;
        minValidator = pMinValidator;
        maxValidator = pMaxValidator;
    }

    private static Character charValueOf(final String pValue) {
        if (requireNonNull(pValue, "Value is null").length() != 1) {
            throw new IllegalArgumentException(format("Value must have exactly one characater, invalid value %s", pValue));
        }
        return pValue.charAt(0);
    }

    private static <T> T same(final T pValue) {
        return pValue;
    }

    private static int length(final String pValue) {
        return pValue.length();
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Function<String, T> getValueConverter() {
        return valueConverter;
    }

    public Function<String, U> getValidationConverter() {
        return validationConverter;
    }

    public BiFunction<T, U, String> getMinValidator() {
        return minValidator;
    }

    public BiFunction<T, U, String> getMaxValidator() {
        return maxValidator;
    }

    public static Type<?, ?> valueOf(final String pName) {
        for (final Type<?, ?> type : TYPES) {
            if (pName.equals(type.getName())) {
                return type;
            }
        }

        throw new IllegalArgumentException(format("No constant found with name %s", pName));
    }
}


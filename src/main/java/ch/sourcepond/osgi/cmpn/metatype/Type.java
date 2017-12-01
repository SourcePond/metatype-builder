package ch.sourcepond.osgi.cmpn.metatype;

import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.osgi.service.metatype.AttributeDefinition.BOOLEAN;
import static org.osgi.service.metatype.AttributeDefinition.BYTE;
import static org.osgi.service.metatype.AttributeDefinition.CHARACTER;
import static org.osgi.service.metatype.AttributeDefinition.DOUBLE;
import static org.osgi.service.metatype.AttributeDefinition.FLOAT;
import static org.osgi.service.metatype.AttributeDefinition.INTEGER;
import static org.osgi.service.metatype.AttributeDefinition.LONG;
import static org.osgi.service.metatype.AttributeDefinition.PASSWORD;
import static org.osgi.service.metatype.AttributeDefinition.SHORT;
import static org.osgi.service.metatype.AttributeDefinition.STRING;

enum Type {
    String(STRING, Type::same),
    Long(LONG, java.lang.Long::valueOf),
    Double(DOUBLE, java.lang.Double::valueOf),
    Float(FLOAT, java.lang.Float::valueOf),
    Integer(INTEGER, java.lang.Integer::valueOf),
    Byte(BYTE, java.lang.Byte::valueOf),
    Char(CHARACTER, Type::charValueOf),
    Boolean(BOOLEAN, java.lang.Boolean::valueOf),
    Short(SHORT, java.lang.Short::valueOf),
    Password(PASSWORD, Type::same);

    private final int value;
    private final Function<String, ?> converter;

    Type(final int pValue, final Function<String, ?> pConverter) {
        value = pValue;
        converter = pConverter;
    }

    private static Character charValueOf(final String pValue) {
        if (requireNonNull(pValue, "Value is null").length() != -1) {
            throw new IllegalArgumentException(format("Value must have exactly one characater, invalid value %s", pValue));
        }
        return pValue.charAt(0);
    }

    private static String same(final String pValue) {
        return pValue;
    }

    public int getValue() {
        return value;
    }

    public Function<String, ?> getConverter() {
        return converter;
    }

    public static Type getType(final int pType) {
        for (Type t : values()) {
            if (pType == t.getValue()) {
                return t;
            }
        }
        throw new IllegalArgumentException(format("No constant found for value %d", pType));
    }
}


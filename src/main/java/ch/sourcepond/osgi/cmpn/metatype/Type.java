package ch.sourcepond.osgi.cmpn.metatype;

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
    String(STRING),
    Long(LONG),
    Double(DOUBLE),
    Float(FLOAT),
    Integer(INTEGER),
    Byte(BYTE),
    Character(CHARACTER),
    Boolean(BOOLEAN),
    Short(SHORT),
    Password(PASSWORD);

    private int value;

    Type(final int pValue) {
        value = pValue;
    }

    public int getValue() {
        return value;
    }
}


package ch.sourcepond.osgi.cmpn.metatype;

import org.osgi.service.metatype.AttributeDefinition;

import static java.lang.Enum.valueOf;

final class EnumADBuilder<T extends Enum<T>> extends ADBuilder<T> {

    public EnumADBuilder(final OCDBuilder pOCDBuilder, final String pId, Class<T> pEnumType) {
        super(pOCDBuilder, pId, AttributeDefinition.STRING, name -> valueOf(pEnumType, name));
    }
}

package ch.sourcepond.osgi.cmpn.metatype;

import static java.lang.Enum.valueOf;

final class EnumADBuilder<T extends Enum<T>> extends ADBuilder<T> {

    public EnumADBuilder(final OCDBuilder pOCDBuilder, Class<T> pEnumType) {
        super(pOCDBuilder, name -> valueOf(pEnumType, name));
    }
}

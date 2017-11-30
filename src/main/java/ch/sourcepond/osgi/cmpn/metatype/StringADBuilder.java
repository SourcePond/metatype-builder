package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.STRING;

final class StringADBuilder extends ADBuilder<String> {

    public StringADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, STRING, StringADBuilder::valueOf);
    }

    public static String valueOf(final String pValue) {
        return pValue;
    }
}

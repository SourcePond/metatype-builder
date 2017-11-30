package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.STRING;

final class PasswordADBuilder extends ADBuilder<String> {

    public PasswordADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, STRING, StringADBuilder::valueOf);
    }

    private static String valueOf(final String pValue) {
        return pValue;
    }
}

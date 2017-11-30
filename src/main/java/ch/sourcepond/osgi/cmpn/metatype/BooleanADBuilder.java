package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.BOOLEAN;

final class BooleanADBuilder extends ADBuilder<Boolean> {

    public BooleanADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, BOOLEAN, Boolean::valueOf);
    }
}

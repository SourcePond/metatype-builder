package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.INTEGER;

final class IntegerADBuilder extends ADBuilder<Integer> {

    public IntegerADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, INTEGER, Integer::valueOf);
    }
}

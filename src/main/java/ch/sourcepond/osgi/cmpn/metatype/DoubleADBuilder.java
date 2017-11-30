package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.DOUBLE;

final class DoubleADBuilder extends ADBuilder<Double> {

    public DoubleADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, DOUBLE, Double::valueOf);
    }
}

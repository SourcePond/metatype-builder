package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.FLOAT;

final class FloatADBuilder extends ADBuilder<Float> {

    public FloatADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, FLOAT, Float::valueOf);
    }
}

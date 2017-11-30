package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.LONG;

final class LongADBuilder extends ADBuilder<Long> {

    public LongADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, LONG, Long::valueOf);
    }
}

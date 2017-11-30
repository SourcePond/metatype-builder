package ch.sourcepond.osgi.cmpn.metatype;

import static org.osgi.service.metatype.AttributeDefinition.BYTE;

final class ByteADBuilder extends ADBuilder<Byte> {

    public ByteADBuilder(final OCDBuilder pOCDBuilder, final String pId) {
        super(pOCDBuilder, pId, BYTE, Byte::valueOf);
    }
}

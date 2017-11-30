package ch.sourcepond.osgi.cmpn.metatype;

final class ByteADBuilder extends ADBuilder<Byte> {

    public ByteADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Byte::valueOf);
    }
}

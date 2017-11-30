package ch.sourcepond.osgi.cmpn.metatype;

final class FloatADBuilder extends ADBuilder<Float> {

    public FloatADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Float::valueOf);
    }
}

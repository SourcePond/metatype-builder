package ch.sourcepond.osgi.cmpn.metatype;

final class DoubleADBuilder extends ADBuilder<Double> {

    public DoubleADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Double::valueOf);
    }
}

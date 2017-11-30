package ch.sourcepond.osgi.cmpn.metatype;

final class IntegerADBuilder extends ADBuilder<Integer> {

    public IntegerADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Integer::valueOf);
    }
}

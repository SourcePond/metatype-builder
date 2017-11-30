package ch.sourcepond.osgi.cmpn.metatype;

final class LongADBuilder extends ADBuilder<Long> {

    public LongADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Long::valueOf);
    }
}

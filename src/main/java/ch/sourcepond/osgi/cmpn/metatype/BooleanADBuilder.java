package ch.sourcepond.osgi.cmpn.metatype;

final class BooleanADBuilder extends ADBuilder<Boolean> {

    public BooleanADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, Boolean::valueOf);
    }
}

package ch.sourcepond.osgi.cmpn.metatype;

final class StringADBuilder extends ADBuilder<String> {

    public StringADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, StringADBuilder::valueOf);
    }

    public static String valueOf(final String pValue) {
        return pValue;
    }
}

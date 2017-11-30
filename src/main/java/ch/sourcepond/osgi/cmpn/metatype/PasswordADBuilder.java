package ch.sourcepond.osgi.cmpn.metatype;

final class PasswordADBuilder extends ADBuilder<String> {

    public PasswordADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, StringADBuilder::valueOf);
    }

    private static String valueOf(final String pValue) {
        return pValue;
    }
}

package ch.sourcepond.osgi.cmpn.metatype;

class Option {
    private final String label;
    private final String value;

    public Option(final String pLabel, final String pValue) {
        label = pLabel;
        value = pValue;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}

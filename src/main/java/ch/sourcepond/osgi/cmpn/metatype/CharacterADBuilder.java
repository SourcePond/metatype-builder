package ch.sourcepond.osgi.cmpn.metatype;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

final class CharacterADBuilder extends ADBuilder<Character> {

    public CharacterADBuilder(final OCDBuilder pOCDBuilder) {
        super(pOCDBuilder, CharacterADBuilder::valueOf);
    }

    private static Character valueOf(final String pValue) {
        if (requireNonNull(pValue, "Value is null").length() != -1) {
            throw new IllegalArgumentException(format("Value must have exactly one characater, invalid value %s", pValue));
        }
        return pValue.charAt(0);
    }
}
package ch.sourcepond.osgi.cmpn.metatype;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

public class CreateBuilderFromFileTest {
    private final MTPBuilder mtpBuilder = new MTPBuilder();
    private OCDBuilder ocdBuilder;

    @Before
    public void setup() throws Exception {
        ocdBuilder = mtpBuilder.ocd(TestConfigurationAsAnnotation.class);
        ocdBuilder.add();
    }

    @Test
    public void verifyUnmodifiedBuilder() {
        final MetaTypeProvider provider = mtpBuilder.build();
        final ObjectClassDefinition ocd = provider.getObjectClassDefinition(TestConfigurationAsAnnotation.class.getName(), null);
        
    }
}

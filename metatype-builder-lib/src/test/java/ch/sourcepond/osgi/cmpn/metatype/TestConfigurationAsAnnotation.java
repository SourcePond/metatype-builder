/*Copyright (C) 2017 Roland Hauser, <sourcepond@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package ch.sourcepond.osgi.cmpn.metatype;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;

@Retention(RetentionPolicy.RUNTIME)
@ObjectClassDefinition(name = "TestConfigurationAsAnnotation", description = "Test configuration")
public @interface TestConfigurationAsAnnotation {

    @AttributeDefinition(description = "Some string")
    String getString() default "one";

    @AttributeDefinition(description = "Some long", defaultValue = "1")
    long getLong() default 1;

    @AttributeDefinition(description = "Some double", defaultValue = "1")
    double getDouble() default 1;

    @AttributeDefinition(description = "Some float", defaultValue = "1")
    float getFloat() default 1;

    @AttributeDefinition(description = "Some integer", defaultValue = "1")
    int getInteger() default 1;

    @AttributeDefinition(description = "Some byte")
    byte getByte() default 1;

    @AttributeDefinition(description = "Some character")
    char getCharacter() default 'a';

    @AttributeDefinition(description = "Some boolean")
    boolean getBoolean() default true;

    @AttributeDefinition(description = "Some short")
    short getShort() default 1;

    @AttributeDefinition(description = "Some password")
    String getPassword() default "password";

    @AttributeDefinition(description = "Some strings", cardinality = 5, min = "3", max = "30")
    String[] getStrings() default {"one", "two"};

    @AttributeDefinition(description = "Some longs", cardinality = 5, min = "1", max = "10")
    long[] getLongs() default {1, 2};

    @AttributeDefinition(description = "Some doubles", cardinality = 5, min = "1", max = "10")
    double[] getDoubles() default {1, 2};

    @AttributeDefinition(description = "Some floats", cardinality = 5, min = "1", max = "10")
    float[] getFloats() default {1, 2};

    @AttributeDefinition(description = "Some integers", cardinality = 5, min = "1", max = "10")
    int[] getIntegers() default {1, 2};

    @AttributeDefinition(description = "Some bytes", cardinality = 5, min = "1", max = "10")
    byte[] getBytes() default {1, 2};

    @AttributeDefinition(description = "Some characters", cardinality = 5, min = "a", max = "z")
    char[] getCharacters() default {'a', 'b'};

    @AttributeDefinition(description = "Some booleans", cardinality = 5)
    boolean[] getBooleans() default {true, true};

    @AttributeDefinition(description = "Some shorts", cardinality = 5, defaultValue = {"1", "2"}, min = "1", max = "10")
    short[] getShorts() default {1, 2};

    @AttributeDefinition(description = "Some passwords", cardinality = 5, min = "3", max = "30")
    String[] getPasswords() default {"one", "two"};

    @AttributeDefinition(description = "Some timeunit")
    TimeUnit getTimeUnit() default DAYS;

    @AttributeDefinition(description = "Some timeunits", cardinality = 5)
    TimeUnit[] getTimeUnits() default {TimeUnit.DAYS, TimeUnit.HOURS};
}

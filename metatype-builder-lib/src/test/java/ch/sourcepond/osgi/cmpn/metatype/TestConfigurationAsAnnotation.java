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

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;

@ObjectClassDefinition(name = "TestConfigurationAsAnnotation", description = "Test configuration")
public @interface TestConfigurationAsAnnotation {

    @AttributeDefinition(description = "Some string", defaultValue = "one")
    String getString();

    @AttributeDefinition(description = "Some long", defaultValue = "1")
    long getLong();

    @AttributeDefinition(description = "Some double", defaultValue = "1")
    double getDouble();

    @AttributeDefinition(description = "Some float", defaultValue = "1")
    float getFloat();

    @AttributeDefinition(description = "Some integer", defaultValue = "1")
    int getInteger();

    @AttributeDefinition(description = "Some byte", defaultValue = "1")
    byte getByte();

    @AttributeDefinition(description = "Some character", defaultValue = "a")
    char getCharacter();

    @AttributeDefinition(description = "Some boolean", defaultValue = "true")
    boolean getBoolean();

    @AttributeDefinition(description = "Some short", defaultValue = "1")
    short getShort();

    @AttributeDefinition(description = "Some password")
    String getPassword();

    @AttributeDefinition(description = "Some strings",  cardinality = 5, defaultValue = {"one" ,"two"}, min = "3", max = "30")
    String[] getStrings();

    @AttributeDefinition(description = "Some longs", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    long[] getLongs();

    @AttributeDefinition(description = "Some doubles", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    double[] getDoubles();

    @AttributeDefinition(description = "Some floats", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    float[] getFloats();

    @AttributeDefinition(description = "Some integers", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    int[] getIntegers();

    @AttributeDefinition(description = "Some bytes", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    byte[] getBytes();

    @AttributeDefinition(description = "Some characters", cardinality = 5, defaultValue = {"a" ,"b"}, min = "a", max = "z")
    char[] getCharacters();

    @AttributeDefinition(description = "Some booleans", cardinality = 5, defaultValue = {"true" ,"true"})
    boolean[] getBooleans();

    @AttributeDefinition(description = "Some shorts", cardinality = 5, defaultValue = {"1" ,"2"}, min = "1", max = "10")
    short[] getShorts();

    @AttributeDefinition(description = "Some passwords", cardinality = 5, defaultValue = {"1" ,"2"}, min = "3", max = "30")
    String[] getPasswords();

    @AttributeDefinition(description = "Some timeunit")
    TimeUnit getTimeUnit() default DAYS;

    @AttributeDefinition(description = "Some timeunits", cardinality = 5, defaultValue = {"DAYS", "HOURS"})
    TimeUnit[] getTimeUnits();
}

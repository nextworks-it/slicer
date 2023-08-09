package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.TestAccessEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TestAccessEnumConverter implements AttributeConverter<TestAccessEnum, String> {

    @Override
    public String convertToDatabaseColumn(TestAccessEnum testAccessEnum) {
        if(testAccessEnum == null)
            return null;

        return testAccessEnum.toString();
    }

    @Override
    public TestAccessEnum convertToEntityAttribute(String testAccessEnum) {
        return TestAccessEnum.fromValue(testAccessEnum);
    }
}

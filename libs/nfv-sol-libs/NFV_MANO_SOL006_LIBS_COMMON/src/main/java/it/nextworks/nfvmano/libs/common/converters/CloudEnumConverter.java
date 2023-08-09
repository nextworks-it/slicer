package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.CloudEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CloudEnumConverter implements AttributeConverter<CloudEnum, String> {

    @Override
    public String convertToDatabaseColumn(CloudEnum cloudEnum) {
        if(cloudEnum == null)
            return null;

        return cloudEnum.toString();
    }

    @Override
    public CloudEnum convertToEntityAttribute(String cloudEnum) {
        return CloudEnum.fromValue(cloudEnum);
    }
}

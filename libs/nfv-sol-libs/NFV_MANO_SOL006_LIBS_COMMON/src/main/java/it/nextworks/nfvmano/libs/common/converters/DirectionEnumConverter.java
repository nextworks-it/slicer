package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.DirectionEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DirectionEnumConverter implements AttributeConverter<DirectionEnum, String> {

    @Override
    public String convertToDatabaseColumn(DirectionEnum directionEnum) {
        if(directionEnum == null)
            return null;

        return directionEnum.toString();
    }

    @Override
    public DirectionEnum convertToEntityAttribute(String directionEnum) {
        return DirectionEnum.fromValue(directionEnum);
    }
}

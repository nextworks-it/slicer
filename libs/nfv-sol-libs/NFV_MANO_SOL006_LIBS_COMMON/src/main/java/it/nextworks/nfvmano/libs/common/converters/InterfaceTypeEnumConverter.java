package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.InterfaceTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InterfaceTypeEnumConverter implements AttributeConverter<InterfaceTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(InterfaceTypeEnum interfaceTypeEnum) {
        if(interfaceTypeEnum == null)
            return null;

        return interfaceTypeEnum.toString();
    }

    @Override
    public InterfaceTypeEnum convertToEntityAttribute(String interfaceTypeEnum) {
        return InterfaceTypeEnum.fromValue(interfaceTypeEnum);
    }
}

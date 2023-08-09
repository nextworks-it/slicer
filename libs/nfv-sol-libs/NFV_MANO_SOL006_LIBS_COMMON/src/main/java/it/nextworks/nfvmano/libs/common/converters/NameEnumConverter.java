package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.NameEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NameEnumConverter implements AttributeConverter<NameEnum, String> {

    @Override
    public String convertToDatabaseColumn(NameEnum nameEnum) {
        if(nameEnum == null)
            return null;

        return nameEnum.toString();
    }

    @Override
    public NameEnum convertToEntityAttribute(String nameEnum) {
        return NameEnum.fromValue(nameEnum);
    }
}

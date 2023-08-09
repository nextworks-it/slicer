package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.MemPageSizeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemPageSizeEnumConverter implements AttributeConverter<MemPageSizeEnum, String> {

    @Override
    public String convertToDatabaseColumn(MemPageSizeEnum memPageSizeEnum) {
        if(memPageSizeEnum == null)
            return null;

        return memPageSizeEnum.toString();
    }

    @Override
    public MemPageSizeEnum convertToEntityAttribute(String memPageSizeEnum) {
        return MemPageSizeEnum.fromValue(memPageSizeEnum);
    }
}

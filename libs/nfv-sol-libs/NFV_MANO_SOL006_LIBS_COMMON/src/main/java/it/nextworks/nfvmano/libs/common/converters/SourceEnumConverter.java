package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.SourceEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SourceEnumConverter implements AttributeConverter<SourceEnum, String> {

    @Override
    public String convertToDatabaseColumn(SourceEnum sourceEnum) {
        if(sourceEnum == null)
            return null;

        return sourceEnum.toString();
    }

    @Override
    public SourceEnum convertToEntityAttribute(String sourceEnum) {
        return SourceEnum.fromValue(sourceEnum);
    }
}

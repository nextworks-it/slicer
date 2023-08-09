package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.TypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeEnumConverter implements AttributeConverter<TypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(TypeEnum typeEnum) {
        if(typeEnum == null)
            return null;

        return typeEnum.toString();
    }

    @Override
    public TypeEnum convertToEntityAttribute(String typeEnum) {
        return TypeEnum.fromValue(typeEnum);
    }
}

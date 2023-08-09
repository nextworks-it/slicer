package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ScopeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScopeEnumConverter implements AttributeConverter<ScopeEnum, String> {

    @Override
    public String convertToDatabaseColumn(ScopeEnum scopeEnum) {
        if(scopeEnum == null)
            return null;

        return scopeEnum.toString();
    }

    @Override
    public ScopeEnum convertToEntityAttribute(String scopeEnum) {
        return ScopeEnum.fromValue(scopeEnum);
    }
}

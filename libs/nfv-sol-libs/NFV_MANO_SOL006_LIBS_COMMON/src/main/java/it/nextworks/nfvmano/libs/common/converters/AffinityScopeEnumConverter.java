package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.AffinityScopeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AffinityScopeEnumConverter implements AttributeConverter<AffinityScopeEnum, String> {

    @Override
    public String convertToDatabaseColumn(AffinityScopeEnum affinityScopeEnum) {
        if(affinityScopeEnum == null)
            return null;

        return affinityScopeEnum.toString();
    }

    @Override
    public AffinityScopeEnum convertToEntityAttribute(String affinityScopeEnum) {
        return AffinityScopeEnum.fromValue(affinityScopeEnum);
    }
}

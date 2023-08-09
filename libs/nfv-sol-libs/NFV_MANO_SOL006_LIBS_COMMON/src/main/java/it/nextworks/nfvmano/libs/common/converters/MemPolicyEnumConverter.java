package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.MemPolicyEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemPolicyEnumConverter implements AttributeConverter<MemPolicyEnum, String> {

    @Override
    public String convertToDatabaseColumn(MemPolicyEnum memPolicyEnum) {
        if(memPolicyEnum == null)
            return null;

        return memPolicyEnum.toString();
    }

    @Override
    public MemPolicyEnum convertToEntityAttribute(String memPolicyEnum) {
        return MemPolicyEnum.fromValue(memPolicyEnum);
    }
}

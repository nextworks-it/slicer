package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.PolicyEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PolicyEnumConverter implements AttributeConverter<PolicyEnum, String> {

    @Override
    public String convertToDatabaseColumn(PolicyEnum policyEnum) {
        if(policyEnum == null)
            return null;

        return policyEnum.toString();
    }

    @Override
    public PolicyEnum convertToEntityAttribute(String policyEnum) {
        return PolicyEnum.fromValue(policyEnum);
    }
}

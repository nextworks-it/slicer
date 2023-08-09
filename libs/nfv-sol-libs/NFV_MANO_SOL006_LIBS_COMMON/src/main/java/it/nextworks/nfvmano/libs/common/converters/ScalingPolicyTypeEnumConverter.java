package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ScalingPolicyTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScalingPolicyTypeEnumConverter implements AttributeConverter<ScalingPolicyTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(ScalingPolicyTypeEnum scalingPolicyTypeEnum) {
        if(scalingPolicyTypeEnum == null)
            return null;

        return scalingPolicyTypeEnum.toString();
    }

    @Override
    public ScalingPolicyTypeEnum convertToEntityAttribute(String scalingPolicyTypeEnum) {
        return ScalingPolicyTypeEnum.fromValue(scalingPolicyTypeEnum);
    }
}

package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ScalingCriteriaOperationEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScalingCriteriaOperationEnumConverter implements AttributeConverter<ScalingCriteriaOperationEnum, String> {

    @Override
    public String convertToDatabaseColumn(ScalingCriteriaOperationEnum scalingCriteriaOperationEnum) {
        if(scalingCriteriaOperationEnum == null)
            return null;

        return scalingCriteriaOperationEnum.toString();
    }

    @Override
    public ScalingCriteriaOperationEnum convertToEntityAttribute(String scalingCriteriaOperationEnum) {
        return ScalingCriteriaOperationEnum.fromValue(scalingCriteriaOperationEnum);
    }
}

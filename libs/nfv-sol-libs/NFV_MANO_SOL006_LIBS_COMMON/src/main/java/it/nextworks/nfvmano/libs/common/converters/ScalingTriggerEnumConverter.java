package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ScalingTriggerEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScalingTriggerEnumConverter implements AttributeConverter<ScalingTriggerEnum, String> {

    @Override
    public String convertToDatabaseColumn(ScalingTriggerEnum scalingTriggerEnum) {
        if(scalingTriggerEnum == null)
            return null;

        return scalingTriggerEnum.toString();
    }

    @Override
    public ScalingTriggerEnum convertToEntityAttribute(String scalingTriggerEnum) {
        return ScalingTriggerEnum.fromValue(scalingTriggerEnum);
    }
}

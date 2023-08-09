package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.FlowPatternEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FlowPatternEnumConverter implements AttributeConverter<FlowPatternEnum, String> {

    @Override
    public String convertToDatabaseColumn(FlowPatternEnum flowPatternEnum) {
        if(flowPatternEnum == null)
            return null;

        return flowPatternEnum.toString();
    }

    @Override
    public FlowPatternEnum convertToEntityAttribute(String flowPatternEnum) {
        return FlowPatternEnum.fromValue(flowPatternEnum);
    }
}

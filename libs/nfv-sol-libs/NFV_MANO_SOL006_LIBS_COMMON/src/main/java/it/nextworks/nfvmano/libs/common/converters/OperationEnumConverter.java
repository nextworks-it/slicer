package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.OperationEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OperationEnumConverter implements AttributeConverter<OperationEnum, String> {

    @Override
    public String convertToDatabaseColumn(OperationEnum operationEnum) {
        if(operationEnum == null)
            return null;

        return operationEnum.toString();
    }

    @Override
    public OperationEnum convertToEntityAttribute(String operationEnum) {
        return OperationEnum.fromValue(operationEnum);
    }
}

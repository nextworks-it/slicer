package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.RelationalOperationTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RelationalOperationTypeEnumConverter implements AttributeConverter<RelationalOperationTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(RelationalOperationTypeEnum relationalOperationTypeEnum) {
        if(relationalOperationTypeEnum == null)
            return null;

        return relationalOperationTypeEnum.toString();
    }

    @Override
    public RelationalOperationTypeEnum convertToEntityAttribute(String relationalOperationTypeEnum) {
        return RelationalOperationTypeEnum.fromValue(relationalOperationTypeEnum);
    }
}

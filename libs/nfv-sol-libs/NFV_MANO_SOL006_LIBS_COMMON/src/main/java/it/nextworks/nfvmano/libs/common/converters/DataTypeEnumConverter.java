package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.DataTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DataTypeEnumConverter implements AttributeConverter<DataTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(DataTypeEnum dataTypeEnum) {
        if(dataTypeEnum == null)
            return null;

        return dataTypeEnum.toString();
    }

    @Override
    public DataTypeEnum convertToEntityAttribute(String dataTypeEnum) {
        return DataTypeEnum.fromValue(dataTypeEnum);
    }
}

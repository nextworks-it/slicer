package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.DiskFormatEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DiskFormatEnumConverter implements AttributeConverter<DiskFormatEnum, String> {

    @Override
    public String convertToDatabaseColumn(DiskFormatEnum diskFormatEnum) {
        if(diskFormatEnum == null)
            return null;

        return diskFormatEnum.toString();
    }

    @Override
    public DiskFormatEnum convertToEntityAttribute(String diskFormatEnum) {
        return DiskFormatEnum.fromValue(diskFormatEnum);
    }
}

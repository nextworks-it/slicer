package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ContainerFormatEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ContainerFormatEnumConverter implements AttributeConverter<ContainerFormatEnum, String> {

    @Override
    public String convertToDatabaseColumn(ContainerFormatEnum containerFormatEnum) {
        if(containerFormatEnum == null)
            return null;

        return containerFormatEnum.toString();
    }

    @Override
    public ContainerFormatEnum convertToEntityAttribute(String containerFormatEnum) {
        return ContainerFormatEnum.fromValue(containerFormatEnum);
    }
}

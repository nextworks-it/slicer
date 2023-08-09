package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.HelmVersionEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class HelmVersionEnumConverter implements AttributeConverter<HelmVersionEnum, String> {

    @Override
    public String convertToDatabaseColumn(HelmVersionEnum helmVersionEnum) {
        if(helmVersionEnum == null)
            return null;

        return helmVersionEnum.toString();
    }

    @Override
    public HelmVersionEnum convertToEntityAttribute(String helmVersionEnum) {
        return HelmVersionEnum.fromValue(helmVersionEnum);
    }
}

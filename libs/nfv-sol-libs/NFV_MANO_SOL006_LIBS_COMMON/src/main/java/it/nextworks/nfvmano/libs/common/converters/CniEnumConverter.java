package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.CniEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CniEnumConverter implements AttributeConverter<CniEnum, String> {

    @Override
    public String convertToDatabaseColumn(CniEnum cniEnum) {
        if(cniEnum == null)
            return null;

        return cniEnum.toString();
    }

    @Override
    public CniEnum convertToEntityAttribute(String cniEnum) {
        return CniEnum.fromValue(cniEnum);
    }
}

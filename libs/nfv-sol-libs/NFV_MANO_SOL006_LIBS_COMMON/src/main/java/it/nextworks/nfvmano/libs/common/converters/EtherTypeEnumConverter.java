package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.EtherTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EtherTypeEnumConverter implements AttributeConverter<EtherTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(EtherTypeEnum etherTypeEnum) {
        if(etherTypeEnum == null)
            return null;

        return etherTypeEnum.toString();
    }

    @Override
    public EtherTypeEnum convertToEntityAttribute(String etherTypeEnum) {
        return EtherTypeEnum.fromValue(etherTypeEnum);
    }
}

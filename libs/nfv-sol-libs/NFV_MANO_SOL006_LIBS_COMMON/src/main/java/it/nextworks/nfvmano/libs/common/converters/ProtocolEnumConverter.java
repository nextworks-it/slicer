package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ProtocolEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProtocolEnumConverter implements AttributeConverter<ProtocolEnum, String> {

    @Override
    public String convertToDatabaseColumn(ProtocolEnum protocolEnum) {
        if(protocolEnum == null)
            return null;

        return protocolEnum.toString();
    }

    @Override
    public ProtocolEnum convertToEntityAttribute(String protocolEnum) {
        return ProtocolEnum.fromValue(protocolEnum);
    }
}

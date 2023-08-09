package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.Ipv6AddressModeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class Ipv6AddressModeEnumConverter implements AttributeConverter<Ipv6AddressModeEnum, String> {

    @Override
    public String convertToDatabaseColumn(Ipv6AddressModeEnum ipv6AddressModeEnum) {
        if(ipv6AddressModeEnum == null)
            return null;

        return ipv6AddressModeEnum.toString();
    }

    @Override
    public Ipv6AddressModeEnum convertToEntityAttribute(String ipv6AddressModeEnum) {
        return Ipv6AddressModeEnum.fromValue(ipv6AddressModeEnum);
    }
}

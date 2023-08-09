package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.IpAddressTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IpAddressTypeEnumConverter implements AttributeConverter<IpAddressTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(IpAddressTypeEnum ipAddressTypeEnum) {
        if(ipAddressTypeEnum == null)
            return null;

        return ipAddressTypeEnum.toString();
    }

    @Override
    public IpAddressTypeEnum convertToEntityAttribute(String ipAddressTypeEnum) {
        return IpAddressTypeEnum.fromValue(ipAddressTypeEnum);
    }
}

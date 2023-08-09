package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.IpVersionEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IpVersionEnumConverter implements AttributeConverter<IpVersionEnum, String> {

    @Override
    public String convertToDatabaseColumn(IpVersionEnum ipVersionEnum) {
        if(ipVersionEnum == null)
            return null;

        return ipVersionEnum.toString();
    }

    @Override
    public IpVersionEnum convertToEntityAttribute(String ipVersionEnum) {
        return IpVersionEnum.fromValue(ipVersionEnum);
    }
}

package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.NetworkTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NetworkTypeEnumConverter implements AttributeConverter<NetworkTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(NetworkTypeEnum networkTypeEnum) {
        if(networkTypeEnum == null)
            return null;

        return networkTypeEnum.toString();
    }

    @Override
    public NetworkTypeEnum convertToEntityAttribute(String networkTypeEnum) {
        return NetworkTypeEnum.fromValue(networkTypeEnum);
    }
}

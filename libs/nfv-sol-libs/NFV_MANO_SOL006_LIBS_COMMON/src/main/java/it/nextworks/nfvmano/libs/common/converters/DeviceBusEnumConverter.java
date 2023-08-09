package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.DeviceBusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DeviceBusEnumConverter implements AttributeConverter<DeviceBusEnum, String> {

    @Override
    public String convertToDatabaseColumn(DeviceBusEnum deviceBusEnum) {
        if(deviceBusEnum == null)
            return null;

        return deviceBusEnum.toString();
    }

    @Override
    public DeviceBusEnum convertToEntityAttribute(String deviceBusEnum) {
        return DeviceBusEnum.fromValue(deviceBusEnum);
    }
}

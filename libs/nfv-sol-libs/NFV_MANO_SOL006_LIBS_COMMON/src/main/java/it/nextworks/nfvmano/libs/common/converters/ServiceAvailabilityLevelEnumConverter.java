package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ServiceAvailabilityLevelEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ServiceAvailabilityLevelEnumConverter implements AttributeConverter<ServiceAvailabilityLevelEnum, String> {

    @Override
    public String convertToDatabaseColumn(ServiceAvailabilityLevelEnum serviceAvailabilityLevelEnum) {
        if(serviceAvailabilityLevelEnum == null)
            return null;

        return serviceAvailabilityLevelEnum.toString();
    }

    @Override
    public ServiceAvailabilityLevelEnum convertToEntityAttribute(String serviceAvailabilityLevelEnum) {
        return ServiceAvailabilityLevelEnum.fromValue(serviceAvailabilityLevelEnum);
    }
}

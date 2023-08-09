package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.PortSecurityDisableStrategyEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PortSecurityDisableStrategyEnumConverter
        implements AttributeConverter<PortSecurityDisableStrategyEnum, String> {

    @Override
    public String convertToDatabaseColumn(PortSecurityDisableStrategyEnum portSecurityDisableStrategyEnum) {
        if(portSecurityDisableStrategyEnum == null)
            return null;

        return portSecurityDisableStrategyEnum.toString();
    }

    @Override
    public PortSecurityDisableStrategyEnum convertToEntityAttribute(String portSecurityDisableStrategyEnum) {
        return PortSecurityDisableStrategyEnum.fromValue(portSecurityDisableStrategyEnum);
    }
}

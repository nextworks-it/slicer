package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.CpuThreadPinningPolicyEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CpuThreadPinningPolicyEnumConverter implements AttributeConverter<CpuThreadPinningPolicyEnum, String> {

    @Override
    public String convertToDatabaseColumn(CpuThreadPinningPolicyEnum cpuThreadPinningPolicyEnum) {
        if(cpuThreadPinningPolicyEnum == null)
            return null;

        return cpuThreadPinningPolicyEnum.toString();
    }

    @Override
    public CpuThreadPinningPolicyEnum convertToEntityAttribute(String cpuThreadPinningPolicyEnum) {
        return CpuThreadPinningPolicyEnum.fromValue(cpuThreadPinningPolicyEnum);
    }
}

package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.AffinityTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AffinityTypeEnumConverter implements AttributeConverter<AffinityTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(AffinityTypeEnum affinityTypeEnum) {
        if(affinityTypeEnum == null)
            return null;

        return affinityTypeEnum.toString();
    }

    @Override
    public AffinityTypeEnum convertToEntityAttribute(String affinityTypeEnum) {
        return AffinityTypeEnum.fromValue(affinityTypeEnum);
    }
}

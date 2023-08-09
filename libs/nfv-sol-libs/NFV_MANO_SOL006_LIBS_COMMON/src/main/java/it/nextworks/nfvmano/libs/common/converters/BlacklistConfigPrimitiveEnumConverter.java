package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.BlacklistConfigPrimitiveEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BlacklistConfigPrimitiveEnumConverter implements AttributeConverter<BlacklistConfigPrimitiveEnum, String> {

    @Override
    public String convertToDatabaseColumn(BlacklistConfigPrimitiveEnum blacklistConfigPrimitiveEnum) {
        if(blacklistConfigPrimitiveEnum == null)
            return null;

        return blacklistConfigPrimitiveEnum.toString();
    }

    @Override
    public BlacklistConfigPrimitiveEnum convertToEntityAttribute(String blacklistConfigPrimitiveEnum) {
        return BlacklistConfigPrimitiveEnum.fromValue(blacklistConfigPrimitiveEnum);
    }
}

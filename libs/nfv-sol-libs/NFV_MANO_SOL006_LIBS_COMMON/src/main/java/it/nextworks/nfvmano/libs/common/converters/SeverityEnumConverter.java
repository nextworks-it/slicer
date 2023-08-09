package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.SeverityEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SeverityEnumConverter implements AttributeConverter<SeverityEnum, String> {

    @Override
    public String convertToDatabaseColumn(SeverityEnum severityEnum) {
        if(severityEnum == null)
            return null;

        return severityEnum.toString();
    }

    @Override
    public SeverityEnum convertToEntityAttribute(String severityEnum) {
        return SeverityEnum.fromValue(severityEnum);
    }
}

package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.ScriptTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScriptTypeEnumConverter implements AttributeConverter<ScriptTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(ScriptTypeEnum scriptTypeEnum) {
        if(scriptTypeEnum == null)
            return null;

        return scriptTypeEnum.toString();
    }

    @Override
    public ScriptTypeEnum convertToEntityAttribute(String scriptTypeEnum) {
        return ScriptTypeEnum.fromValue(scriptTypeEnum);
    }
}

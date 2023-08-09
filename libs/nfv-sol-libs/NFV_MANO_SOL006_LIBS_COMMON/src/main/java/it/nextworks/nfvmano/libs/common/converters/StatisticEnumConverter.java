package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.StatisticEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatisticEnumConverter implements AttributeConverter<StatisticEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatisticEnum statisticEnum) {
        if(statisticEnum == null)
            return null;

        return statisticEnum.toString();
    }

    @Override
    public StatisticEnum convertToEntityAttribute(String statisticEnum) {
        return StatisticEnum.fromValue(statisticEnum);
    }
}

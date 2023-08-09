package it.nextworks.nfvmano.libs.common.converters;

import it.nextworks.nfvmano.libs.common.enums.EventEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EventEnumConverter implements AttributeConverter<EventEnum, String> {

    @Override
    public String convertToDatabaseColumn(EventEnum eventEnum) {
        if(eventEnum == null)
            return null;

        return eventEnum.toString();
    }

    @Override
    public EventEnum convertToEntityAttribute(String eventEnum) {
        return EventEnum.fromValue(eventEnum);
    }
}

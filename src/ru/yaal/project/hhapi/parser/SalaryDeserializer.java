package ru.yaal.project.hhapi.parser;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yaal.project.hhapi.dictionary.Constants;
import ru.yaal.project.hhapi.dictionary.entry.entries.small.Currency;
import ru.yaal.project.hhapi.vacancy.Salary;

import java.lang.reflect.Type;

public class SalaryDeserializer implements JsonDeserializer<Salary> {
    private static final Logger LOG = LoggerFactory.getLogger(SalaryDeserializer.class);

    @Override
    public Salary deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
        try {
            JsonObject object = (JsonObject) element;

            JsonElement toElement = object.get("to");
            Integer to = (toElement.isJsonPrimitive()) ? toElement.getAsInt() : null;

            JsonElement fromElement = object.get("from");
            Integer from = (fromElement.isJsonPrimitive()) ? fromElement.getAsInt() : null;

            JsonElement currencyElement = object.get("currency");
            Currency currency = null;
            if (currencyElement.isJsonPrimitive()) {
                String currencyId = currencyElement.getAsString();
                currency = Constants.Currency.CURRENCIES.getById(currencyId);
            }

            return new Salary(from, to, currency);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new JsonParseException(e);
        }
    }
}
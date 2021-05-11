package jdash.client.response;

import jdash.client.exception.NoTimelyAvailableException;
import jdash.common.entity.GDTimelyInfo;
import jdash.common.entity.ImmutableGDTimelyInfo;

import java.time.Duration;
import java.util.function.Function;

public class TimelyInfoResponseDeserializer implements Function<String, GDTimelyInfo> {

    @Override
    public GDTimelyInfo apply(String response) {
        if (response.length() < 3) {
            throw new NoTimelyAvailableException();
        }
        var tokens = response.split("\\|");
        var number = Long.parseLong(tokens[0]) % 100_000;
        return ImmutableGDTimelyInfo.builder()
                .number(number)
                .nextIn(Duration.ofSeconds(Long.parseLong(tokens[1])))
                .build();
    }
}

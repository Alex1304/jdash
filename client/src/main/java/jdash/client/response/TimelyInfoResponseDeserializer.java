package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDTimelyInfo;
import jdash.common.entity.ImmutableGDTimelyInfo;

import java.time.Duration;
import java.util.function.Function;

class TimelyInfoResponseDeserializer implements Function<String, GDTimelyInfo> {

    @Override
    public GDTimelyInfo apply(String response) {
        if (!response.matches("\\d+\\|\\d+")) {
            throw new ActionFailedException(response, "No daily/weekly is currently set");
        }
        var tokens = response.split("\\|");
        var number = Long.parseLong(tokens[0]) % 100_000;
        return ImmutableGDTimelyInfo.builder()
                .number(number)
                .nextIn(Duration.ofSeconds(Long.parseLong(tokens[1])))
                .build();
    }
}

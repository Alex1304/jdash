package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDDailyInfo;

import java.time.Duration;
import java.util.function.Function;

class DailyInfoResponseDeserializer implements Function<String, GDDailyInfo> {

    @Override
    public GDDailyInfo apply(String response) {
        if (!response.matches("\\d+\\|\\d+")) {
            throw new ActionFailedException(response, "Failed to load daily level info");
        }
        var tokens = response.split("\\|");
        var number = Long.parseLong(tokens[0]) % 100_000;
        return new GDDailyInfo(number, Duration.ofSeconds(Long.parseLong(tokens[1])));
    }
}

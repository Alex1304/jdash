package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

class LoginResponseDeserializer implements Function<String, Tuple2<Long, Long>> {

    @Override
    public Tuple2<Long, Long> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Login failed");
        ActionFailedException.throwIfEquals(response, "-12", "Account suspended");
        var values = response.split(",");
        return Tuples.of(Long.parseLong(values[0]), Long.parseLong(values[1]));
    }
}

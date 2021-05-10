package jdash.client.response;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

public final class LoginResponseDeserializer implements Function<String, Tuple2<Long, Long>> {

    @Override
    public Tuple2<Long, Long> apply(String response) {
        var values = response.split(",");
        return Tuples.of(Long.parseLong(values[0]), Long.parseLong(values[1]));
    }
}

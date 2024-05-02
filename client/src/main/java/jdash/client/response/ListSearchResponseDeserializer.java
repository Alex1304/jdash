package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildList;
import static jdash.common.internal.InternalUtils.splitToMap;

class ListSearchResponseDeserializer implements Function<String, List<GDList>> {

    @Override
    public List<GDList> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load lists");
        final var list = new ArrayList<GDList>();
        final var split1 = response.split("#");
        final var lists = split1[0];
        final var listArray = lists.split("\\|");
        for (final var l : listArray) {
            final var data = splitToMap(l, ":");
            list.add(buildList(data));
        }
        return list;
    }
}

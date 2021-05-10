package jdash.client.response;

import jdash.common.entity.GDLevel;
import jdash.common.entity.GDUser;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.function.Function;

public final class GDResponseDeserializers {

    private static final LevelSearchResponseDeserializer LEVEL_SEARCH_RESPONSE = new LevelSearchResponseDeserializer();
    private static final LoginResponseDeserializer LOGIN_RESPONSE = new LoginResponseDeserializer();
    private static final UserProfileResponseDeserializer USER_PROFILE_RESPONSE = new UserProfileResponseDeserializer();
    private static final UserSearchResponseDeserializer USER_SEARCH_RESPONSE = new UserSearchResponseDeserializer();

    private GDResponseDeserializers() {
        throw new AssertionError();
    }

    public static Function<String, List<GDLevel>> levelSearchResponse() {
        return LEVEL_SEARCH_RESPONSE;
    }

    public static Function<String, Tuple2<Long, Long>> loginResponse() {
        return LOGIN_RESPONSE;
    }

    public static Function<String, GDUser> userProfileResponse() {
        return USER_PROFILE_RESPONSE;
    }

    public static Function<String, List<GDUser>> userSearchResponse() {
        return USER_SEARCH_RESPONSE;
    }
}

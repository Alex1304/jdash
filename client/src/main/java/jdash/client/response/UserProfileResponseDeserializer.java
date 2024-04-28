package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDUserProfile;

import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildUserProfile;
import static jdash.common.internal.InternalUtils.splitToMap;

class UserProfileResponseDeserializer implements Function<String, GDUserProfile> {

    @Override
    public GDUserProfile apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load user profile");
        return buildUserProfile(splitToMap(response, ":"));
    }
}

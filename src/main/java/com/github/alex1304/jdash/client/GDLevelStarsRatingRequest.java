package com.github.alex1304.jdash.client;

import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Routes;
import com.github.alex1304.jdash.util.Utils;

import java.util.Map;
import java.util.Objects;

class GDLevelStarsRatingRequest extends AbstractAuthenticatedGDRequest<Void> {

    private final long levelID;
    private final int stars;
    private final String uuid;
    private final String udid;

    GDLevelStarsRatingRequest(AuthenticatedGDClient client, long levelID, int stars, String udid) {
        super(client);
        this.levelID = levelID;
        this.stars = stars;
        this.uuid = ""+client.getPlayerID();
        this.udid = Objects.requireNonNull(udid);
    }

    @Override
    public String getPath() {
        return Routes.RATE_LEVEL_STARS;
    }

    @Override
    void putParams(Map<String, String> params) {
        String rs = Utils.randomString(10);
        String chk = Utils.buildChk("58281", ""+levelID, ""+stars, rs, ""+client.getAccountID(), udid, uuid, "ysg6pUrtjn0J");
        params.put("udid", udid);
        params.put("uuid", uuid);
        params.put("levelID", ""+levelID);
        params.put("stars", ""+stars);
        params.put("rs", rs);
        params.put("chk", chk);
    }

    @Override
    Void parseResponse0(String response) throws GDClientException {
        if (!response.equals("1")) {
            throw new IllegalArgumentException("Unknown response: " + response);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GDLevelStarsRatingRequest)){
            return false;
        }
        GDLevelStarsRatingRequest r = (GDLevelStarsRatingRequest) obj;
        return r.levelID == levelID && r.stars == stars && r.udid.equals(udid);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(levelID);
    }
}

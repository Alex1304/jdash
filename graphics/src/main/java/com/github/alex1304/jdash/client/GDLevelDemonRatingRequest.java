package com.github.alex1304.jdash.client;

import com.github.alex1304.jdash.entity.DemonDifficulty;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.RatingFailedException;
import com.github.alex1304.jdash.util.Routes;

import java.util.Map;
import java.util.Objects;

class GDLevelDemonRatingRequest extends AbstractAuthenticatedGDRequest<Void> {

    private final long levelID;
    private final DemonDifficulty difficulty;

    GDLevelDemonRatingRequest(AuthenticatedGDClient client, long levelID, DemonDifficulty difficulty) {
        super(client);
        this.levelID = levelID;
        this.difficulty = Objects.requireNonNull(difficulty);
    }

    @Override
    public String getPath() {
        return Routes.RATE_LEVEL_DEMON;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("levelID", ""+levelID);
        params.put("rating", ""+(difficulty.ordinal()+1));
        params.put("secret", "Wmfp3879gc3"); //Overrides the default one
    }

    @Override
    Void parseResponse0(String response) throws GDClientException {
        if (!response.equals("1")) {
            throw new RatingFailedException();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GDLevelDemonRatingRequest)){
            return false;
        }
        GDLevelDemonRatingRequest r = (GDLevelDemonRatingRequest) obj;
        return r.levelID == levelID && r.difficulty == difficulty;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(levelID);
    }
}

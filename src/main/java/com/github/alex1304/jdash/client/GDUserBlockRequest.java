package com.github.alex1304.jdash.client;

import com.github.alex1304.jdash.exception.BlockingUserFailedException;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Routes;

import java.util.Map;

class GDUserBlockRequest extends AbstractAuthenticatedGDRequest<Void> {

    private final long targetAccountID;

    GDUserBlockRequest(AuthenticatedGDClient client, long targetAccountID) {
        super(client);
        this.targetAccountID = targetAccountID;
    }

    @Override
    public String getPath() {
        return Routes.BLOCK_USER;
    }

    @Override
    void putParams(Map<String, String> params) {
        params.put("targetAccountID", ""+targetAccountID);
    }

    @Override
    Void parseResponse0(String response) throws GDClientException {
        if (!response.equals("1")) {
            throw new BlockingUserFailedException();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GDUserBlockRequest && ((GDUserBlockRequest) obj).targetAccountID == targetAccountID;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(targetAccountID);
    }
}


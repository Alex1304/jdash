package com.github.alex1304.jdash.client;

import java.util.Map;

import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.MissingAccessException;
import com.github.alex1304.jdash.exception.SongNotAllowedForUseException;
import com.github.alex1304.jdash.util.ParseUtils;
import com.github.alex1304.jdash.util.Routes;

class GDSongInfoRequest extends AbstractGDRequest<GDSong> {
	
	private final long songId;
	
	GDSongInfoRequest(AbstractGDClient client, long songId) {
		super(client);
		this.songId = songId;
	}

	@Override
	public String getPath() {
		return Routes.GET_SONG_INFO;
	}

	@Override
	void putParams(Map<String, String> params) {
		params.put("songID", "" + songId);
	}

	@Override
	GDSong parseResponse0(String response) throws GDClientException {
		if (response.equals("-2")) {
			throw new SongNotAllowedForUseException();
		}
		Map<Long, GDSong> songInfo = ParseUtils.structureSongsInfo(response);
		if (!songInfo.containsKey(songId)) {
			throw new MissingAccessException();
		}
		return songInfo.get(songId);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDSongInfoRequest && ((GDSongInfoRequest) obj).songId == songId;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(songId);
	}
}

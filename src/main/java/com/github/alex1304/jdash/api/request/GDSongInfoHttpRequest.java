package com.github.alex1304.jdash.api.request;

import java.util.Map;

import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDSong;
import com.github.alex1304.jdash.util.Utils;

/**
 * Requests info on the given song ID
 *
 * @author Alex1304
 */
public class GDSongInfoHttpRequest extends GDHttpRequest<GDSong> {
	
	private long songID;

	public GDSongInfoHttpRequest(long songID) {
		super("/getGJSongInfo.php", false);
		String secret = this.getParams().get("secret");
		this.getParams().clear();
		this.getParams().put("secret", secret);
		this.getParams().put("songID", "" + songID);
		this.songID = songID;
	}

	@Override
	public GDHttpResponseBuilder<GDSong> responseBuilderInstance() {
		return response -> {
			if (response.startsWith("-"))
				return null;
			
			Map<Long, GDSong> songInfo = Utils.structureSongsInfo(response);
			if (!songInfo.containsKey(songID))
				return null;
			
			return songInfo.get(songID);
		};
	}

}

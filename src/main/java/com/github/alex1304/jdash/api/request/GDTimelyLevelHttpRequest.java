package com.github.alex1304.jdash.api.request;

import com.github.alex1304.jdash.api.GDHttpClient;
import com.github.alex1304.jdash.api.GDHttpRequest;
import com.github.alex1304.jdash.api.GDHttpResponseBuilder;
import com.github.alex1304.jdash.component.GDLevel;
import com.github.alex1304.jdash.component.GDSong;
import com.github.alex1304.jdash.component.GDTimelyLevel;
import com.github.alex1304.jdash.exceptions.GDAPIException;

/**
 * Request to fetch timely level info
 *
 * @author Alex1304
 */
public class GDTimelyLevelHttpRequest extends GDHttpRequest<GDTimelyLevel> {

	private GDLevelHttpRequest lvlReq;
	private GDHttpClient client;
	
	public GDTimelyLevelHttpRequest(boolean weekly, GDHttpClient client) {
		super("/getGJDailyLevel.php", false);
		this.getParams().put("weekly", weekly ? "1" : "0");
		this.lvlReq = new GDLevelHttpRequest(weekly ? -2 : -1);
		this.client = client;
	}

	@Override
	public GDHttpResponseBuilder<GDTimelyLevel> responseBuilderInstance() {
		return response -> {
			if (!response.matches("[0-9]+\\|[0-9]+"))
				return null;
			
			GDLevel lvl = null;
			
			try {
				lvl = client.fetch(lvlReq);
				if (lvl == null)
					return null;
				
				if (lvl.getSong().isCustom()) {
					GDSong song = client.fetch(new GDSongInfoHttpRequest(lvl.getSong().getSongID()));
					if (song == null)
						song = GDSong.unknownSong(lvl.getSong().getSongID());
					lvl.setSong(song);
				}
			} catch (GDAPIException e) {
				return null;
			}
			
			String[] split = response.split("\\|");
			return new GDTimelyLevel(lvl, Long.parseLong(split[0]), Long.parseLong(split[1]));
		};
	}

}

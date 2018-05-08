package com.alex1304dev.jdash.api.request;

import java.util.Base64;
import java.util.Map;

import com.alex1304dev.jdash.api.GDHttpRequest;
import com.alex1304dev.jdash.api.GDHttpResponseBuilder;
import com.alex1304dev.jdash.component.GDLevel;
import com.alex1304dev.jdash.component.property.GDLevelLength;
import com.alex1304dev.jdash.util.Constants;
import com.alex1304dev.jdash.util.Utils;
import com.alex1304dev.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

/**
 * HTTP Request to fetch a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelHttpRequest extends GDHttpRequest<GDLevel> {

	/**
	 * @param levelID - the ID of the level to fetch
	 */
	public GDLevelHttpRequest(long levelID) {
		super("/downloadGJLevel22.php", false);
		this.getParams().put("levelID", "" + levelID);
	}

	@Override
	public GDHttpResponseBuilder<GDLevel> responseBuilderInstance() {
		return response -> {
			if (response.equals("-1"))
				return null;
			
			String[] split1 = response.split("#");
			String levelData = split1[0];
			Map<Integer, String> mappedLevelData = Utils.splitToMap(levelData, ":");
			
			return new GDLevel(
				Long.parseLong(mappedLevelData.get(Constants.INDEX_LEVEL_ID)),
				mappedLevelData.get(Constants.INDEX_LEVEL_NAME),
				Long.parseLong(mappedLevelData.get(Constants.INDEX_LEVEL_CREATOR_ID)),
				new String(Base64.getUrlDecoder().decode(mappedLevelData.get(Constants.INDEX_LEVEL_DESCRIPTION))),
				Constants.VALUE_TO_DIFFICULTY.apply(Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_DIFFICULTY))),
				Constants.VALUE_TO_DEMON_DIFFICULTY.apply(Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_DEMON_DIFFICULTY))),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_STARS)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_FEATURED_SCORE)),
				mappedLevelData.get(Constants.INDEX_LEVEL_IS_EPIC).equals("1"),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_DOWNLOADS)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_LIKES)),
				GDLevelLength.values()[Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_LENGTH))],
				Integer.parseInt(RobTopsWeakCrypto.decodeGDAccountPassword(mappedLevelData.get(Constants.INDEX_LEVEL_PASS))),
				Long.parseLong(mappedLevelData.get(Constants.INDEX_LEVEL_SONG_ID)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_COIN_COUNT)),
				mappedLevelData.get(Constants.INDEX_LEVEL_COIN_VERIFIED).equals("1"),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_VERSION)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_GAME_VERSION)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_OBJECT_COUNT)),
				mappedLevelData.get(Constants.INDEX_LEVEL_IS_DEMON).equals("1"),
				mappedLevelData.get(Constants.INDEX_LEVEL_IS_AUTO).equals("1"),
				Long.parseLong(mappedLevelData.get(Constants.INDEX_LEVEL_ORIGINAL)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_AUDIO_TRACK)),
				Integer.parseInt(mappedLevelData.get(Constants.INDEX_LEVEL_REQUESTED_STARS)),
				mappedLevelData.get(Constants.INDEX_LEVEL_UPLOADED_TIMESTAMP),
				mappedLevelData.get(Constants.INDEX_LEVEL_LAST_UPDATED_TIMESTAMP)
			);
		};
	}

}

package jdash.client.response;

import jdash.common.IconType;
import jdash.common.entity.GDUser;
import jdash.common.internal.InternalUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;

public class UserSearchResponseDeserializer implements Function<String, List<GDUser>> {

    @Override
    public List<GDUser> apply(String response) {
        var list = new ArrayList<GDUser>();
        var split1 = response.split("#");
        var split2 = split1[0].split("\\|");
        for (String u : split2) {
            Map<Integer, String> data = InternalUtils.splitToMap(u, ":");
            int iconTypeIndex = Integer.parseInt(data.getOrDefault(USER_ICON_TYPE, "0"));
            list.add(InternalUtils.initUserBuilder(data)
                    .hasGlowOutline(!data.getOrDefault(USER_GLOW_OUTLINE, "0").equals("0"))
                    .mainIconId(Integer.parseInt(data.getOrDefault(USER_ICON, "0")))
                    .mainIconType(IconType.values()[iconTypeIndex >= IconType.values().length ? 0 : iconTypeIndex])
                    .build());
        }
        return list;
    }
}

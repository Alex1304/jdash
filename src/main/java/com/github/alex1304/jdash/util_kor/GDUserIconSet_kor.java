package com.github.alex1304.jdash.util;
import java.awt.image.BufferedImage;
import java.util.Objects;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.IconType;
import com.github.alex1304.jdash.graphics.SpriteFactory;
/**
 * Allows to generate user icons.
(사용자 아이콘 생성 허용) */
public class GDUserIconSet
{
private final GDUser user;
private final SpriteFactory factory;
public GDUserIconSet(GDUser user, SpriteFactory factory)
{
this.user = Objects.requireNonNull(user);
this.factory = Objects.requireNonNull(factory);
}
/**
 * Generates the icon of the specified type for the underlying user.
(기본 사용자에 대해 지정된 유형의 아이콘 생성)
* 
* @param iconType the typeof icon to generate
(@param 아이콘생성할 아이콘 유형 입력)
 * @return the requested icon as a {@link BufferedImage}
	(@요구된 아이콘을 {@link BufferedImage}(으)로 반환) */
public BufferedImage generateIcon(IconType iconType)
{
return factory.makeSprite(iconType, Math.max(1, iconType.idForUser(user)), user.getColor1Id(), user.getColor2Id(), user.hasGlowOutline());
}
/**
* Two icon sets are equal if:
(다음과 같은 경우 두 아이콘 세트가 동일함) * <ul>
 * <li>For each icon type, both users have the same icon ID and the same

* color.</li> (각 아이콘 유형에 대해 두 사용자 모두 동일한 아이콘 ID와 동일한 아이콘 색깔을 가짐)
* <li>Both users either have the glow outline on their icons or do not have
(두 사용자 모두 아이콘에 glow outline 을 표시하거나 표시하지않아야한다.)
* it.</li>
* </ul>
*/

@Override

public boolean equals(Object obj)
{
if (!(obj instanceof GDUserIconSet))
{
return false;
}
GDUserIconSet o = (GDUserIconSet) obj;
if (user.equals(o.user))
{
return true;
}
for (IconType t : IconType.values())
{
if (t.idForUser(user) != t.idForUser(o.user))
{
return false;
}
}
return user.getColor1Id() == o.user.getColor1Id() && user.getColor2Id() == o.user.getColor2Id()
&& user.hasGlowOutline() == o.user.hasGlowOutline();
}

@Override

public int hashCode()
{
int hash = user.getColor1Id() ^ user.getColor2Id() ^ Boolean.hashCode(user.hasGlowOutline());
for (IconType t : IconType.values())
{
hash ^= t.idForUser(user);
}

return hash;
}
}

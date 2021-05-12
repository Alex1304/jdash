package jdash.client.response;

import jdash.common.entity.GDComment;
import jdash.common.entity.GDUser;
import jdash.common.entity.ImmutableGDComment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.*;

public class CommentsResponseDeserializer implements Function<String, List<GDComment>> {

    @Override
    public List<GDComment> apply(String response) {
        var list = new ArrayList<GDComment>();
        for (var comment : response.split("#")[0].split("\\|")) {
            var parts = comment.split(":");
            var commentData = splitToMap(parts[0], "~");
            requireKeys(commentData, COMMENT_ID, COMMENT_CONTENT, COMMENT_LIKES, COMMENT_POSTED_AGO,
                    COMMENT_AUTHOR_PLAYER_ID);
            var authorData = splitToMap(parts[1], "~");
            GDUser author = null;
            if (!authorData.getOrDefault(USER_ACCOUNT_ID, "").isEmpty()) {
                authorData.put(USER_PLAYER_ID, commentData.get(COMMENT_AUTHOR_PLAYER_ID));
                authorData.put(USER_ROLE, commentData.getOrDefault(COMMENT_AUTHOR_ROLE, "0"));
                author = buildUser(authorData);
            }
            list.add(ImmutableGDComment.builder()
                    .id(Long.parseLong(commentData.get(COMMENT_ID)))
                    .author(Optional.ofNullable(author))
                    .content(b64Decode(commentData.get(COMMENT_CONTENT)))
                    .likes(Integer.parseInt(commentData.get(COMMENT_LIKES)))
                    .postedAgo(commentData.get(COMMENT_POSTED_AGO))
                    .percentage(Optional.ofNullable(commentData.get(COMMENT_PERCENTAGE))
                            .map(Integer::parseInt))
                    .color(Optional.ofNullable(commentData.get(COMMENT_COLOR))
                            .map(color -> {
                                var rgb = Arrays.stream(color.split(","))
                                        .map(Integer::parseInt)
                                        .toArray(Integer[]::new);
                                return 0xFFFFFF & (rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
                            }))
                    .build());
        }
        return list;
    }
}

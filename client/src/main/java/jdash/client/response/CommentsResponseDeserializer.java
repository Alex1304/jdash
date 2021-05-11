package jdash.client.response;

import jdash.common.Role;
import jdash.common.entity.GDComment;
import jdash.common.entity.ImmutableGDComment;
import jdash.common.internal.InternalUtils;

import java.util.*;
import java.util.function.Function;

import static java.util.function.Predicate.not;
import static jdash.common.internal.Indexes.*;

public class CommentsResponseDeserializer implements Function<String, List<GDComment>> {

    @Override
    public List<GDComment> apply(String response) {
        var list = new ArrayList<GDComment>();
        for (var comment : response.split("#")[0].split("\\|")) {
            var tokens = comment.split(":");
            var commentData = InternalUtils.splitToMap(tokens[0], "~");
            var authorData = tokens.length == 1 ? Map.<Integer, String>of()
                    : InternalUtils.splitToMap(tokens[1], "~");
            list.add(ImmutableGDComment.builder()
                    .id(Long.parseLong(commentData.getOrDefault(COMMENT_ID, "0")))
                    .authorPlayerId(Optional.ofNullable(commentData.get(COMMENT_AUTHOR_PLAYER_ID))
                            .filter(not(String::isEmpty))
                            .map(Long::parseLong))
                    .authorAccountId(Optional.ofNullable(authorData.get(USER_ACCOUNT_ID))
                            .filter(not(String::isEmpty))
                            .map(Long::parseLong))
                    .authorName(Optional.ofNullable(authorData.get(USER_NAME)))
                    .authorRole(Optional.ofNullable(commentData.get(COMMENT_AUTHOR_ROLE))
                            .map(Integer::parseInt)
                            .map(role -> {
                                var roles = Role.values();
                                return roles[role < 0 || role >= roles.length ? 0 : role];
                            }))
                    .content(InternalUtils.b64Decode(commentData.getOrDefault(COMMENT_CONTENT, "")))
                    .likes(Integer.parseInt(commentData.getOrDefault(COMMENT_LIKES, "0")))
                    .postedAgo(commentData.getOrDefault(COMMENT_POSTED_AGO, "NA"))
                    .percentage(Optional.ofNullable(commentData.get(COMMENT_PERCENTAGE))
                            .filter(not(String::isEmpty))
                            .map(Integer::parseInt))
                    .color(Optional.ofNullable(commentData.get(COMMENT_COLOR))
                            .filter(not(String::isEmpty))
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

package jdash;

public class TestMain {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Please provide GD account login details in arguments");
            return;
        }
        /*AuthenticatedGDClient client = GDClientBuilder.create()
                .buildAuthenticated(new GDClientBuilder.Credentials(args[0], args[1]))
                .block();

        Mono.when(

                client.getUserByAccountId(98006)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get user 98006", o)),

                client.getCommentsForUser(855735, 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get comments of user 855735", o)),

                client.searchUser("RobTop")
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Search user RobTop", o)),

                client.getLevelById(10565740)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get level 10565740", o)),

                client.searchLevels("bloodbath", LevelSearchFilter.create(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Search levels Bloodbath", o)),

                client.searchLevels("sonic wave",
                        LevelSearchFilter.create().withDifficulties(EnumSet.of(Difficulty.HARD)), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Search levels Sonic wave, filter: Difficulty.HARD", o)),

                client.getLevelById(52637920)
                        .doOnError(Throwable::printStackTrace)
                        .flatMap(GDLevel::download)
                        .doOnSuccess(o -> printResult("Download level 52637920", o)),

                client.getCommentsForLevel(49994214, CommentSortMode.MOST_LIKED, 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get comments of level 49994214, mode: Most Liked", o)),

                client.getDailyLevel()
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get Daily level info", o)),

                client.getWeeklyDemon()
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get Weekly demon info", o)),

                client.getDailyLevel()
                        .flatMap(GDTimelyLevel::getLevel)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get Daily level download", o)),

                client.getWeeklyDemon()
                        .flatMap(GDTimelyLevel::getLevel)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get Weekly demon download", o)),

                client.browseAwardedLevels(LevelSearchFilter.create(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Awarded section", o)),

                client.browseRecentLevels(LevelSearchFilter.create(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Recent section", o)),

                client.browseMagicLevels(LevelSearchFilter.create(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Magic section", o)),

                client.browseTrendingLevels(LevelSearchFilter.create(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Trending section", o)),


                client.browseFeaturedLevels(0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Featured section", o)),

                client.browseHallOfFameLevels(0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse Hall of Fame", o)),

                client.getLevelsByUser(client.getUserByAccountId(98006).block(), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Get levels from Alex1304", o)),

                client.browseAwardedLevels(LevelSearchFilter.create(), 0)
                        .flatMap(GDPaginator::goToNextPage)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Browse second page of Awarded section", o)),

                client.browseFollowedIds(LevelSearchFilter.create(), new ArrayList<>(Arrays.asList(98006L, 71L)), 0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Following Alex1304 and RobTop", o)),

                client.getPrivateMessages(0)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Private messages", o)),

                client.getPrivateMessages(0)
                        .map(paginator -> paginator.asList().get(0))
                        .flatMap(GDMessage::getBody)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("First private message content", o)),

                client.sendPrivateMessage(client.searchUser("Alex1304").block(), "Test", "Hello world!")
                        .doOnSuccess(o -> printResult("Send message", "Message sent!"))
                        .doOnError(Throwable::printStackTrace),

                client.getLeaderboard(LeaderboardType.FRIENDS, 50)
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("My friend ranking", o)),

                client.getLeaderboard(LeaderboardType.CREATORS, 200)
                        .map(list -> list.get(149).getCreatorPoints())
                        .doOnError(Throwable::printStackTrace)
                        .doOnSuccess(o -> printResult("Creators ranking 150th user's cp", o))

        ).block();*/

        System.out.println("End program");
    }

    private static void printResult(String title, Object obj) {
        System.out.println("------- " + title + " -------\n\t" + obj + "\n");
    }
}

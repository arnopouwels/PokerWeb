/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import Users.Game;
import Users.User;
import Users.UserGame;
import cards.Card;
import com.google.inject.Inject;
import ninja.Result;
import ninja.Results;

import ninja.Router;
import ninja.params.PathParam;

import com.google.inject.Singleton;
import ninja.session.FlashScope;
import services.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ninja.Context;


@Singleton
public class ApplicationController {

    @Inject private PokerService pokerService;
    @Inject private RegisterService registerService;
    @Inject private loginService loginService;
    @Inject private multiplayerService multiplayerService;
    @Inject private userGameService userGameService;
    @Inject private Router router;


    public Result gameResults(@PathParam("gameName") String gameName, Context context, FlashScope flashScope) {
        Result result = Results.html();

        List<UserGame> usersInGame = userGameService.UserGameGet(gameName);
        UserGame userGame = userGameService.UserGameGet(gameName).get(0);
        Game game = userGame.getG();
        String userName = context.getSession().get("username");

        // Get winner. For now it rigged to be the host
        String winner = game.getHost();
        flashScope.success("Winner is "+winner);

        // Pay out the winner and update database
        if (game.getActive()) {
            int numOfPlayers = usersInGame.size();
            for (int u = 0; u < numOfPlayers; u++) {
                User player = usersInGame.get(u).getU();
                int moneyChangeAmount = 0;

                if (player.getUsername().equals(winner)) {
                    moneyChangeAmount = (numOfPlayers - 1) * game.getCostToPlay();
                } else {
                    moneyChangeAmount = - game.getCostToPlay();
                }

                player.setMoney(player.getMoney() + moneyChangeAmount);
                userGameService.updateUserInDatabase(player);
            }
            game.setActive(false);
            userGameService.updateGameInDatabase(game);
        }

        String text = "";

        text += "<table id=\"tableGames\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">" +
                "<thead>" +
                "<tr><th>" + userGame.getGameName() + "</th></tr>" +
                "        <tr>" +
                "        <th>Username</th>" +
                "        <th>Hand</th>" +
                "        <th>Date</th>" +
                "        </tr>" +
                "        </thead>";

        for (int j = 0; j < usersInGame.size(); j++) {

            String uig = usersInGame.get(j).getHand();
            uig = uig.replace("(", "");
            uig = uig.replace(")", "");
            String[] arrayOne = uig.split(",");

            text += "<tr>";
            text += "<td>" + usersInGame.get(j).getUsername() + "</td>";
            text += "<td>" + "<img src=\"/assets/images/PlayingCards/" + pokerService.getImage(new Card(arrayOne[0])) + ".png\" height=\"80\" width=\"50\" id=\"card1\" style=\"position:relative;\" />\n" +
                    "        <img src=\"/assets/images/PlayingCards/" + pokerService.getImage(new Card(arrayOne[1])) + ".png\" height=\"80\" width=\"50\" id=\"card2\" style=\"position:relative;\" />\n" +
                    "        <img src=\"/assets/images/PlayingCards/" + pokerService.getImage(new Card(arrayOne[2])) + ".png\" height=\"80\" width=\"50\" id=\"card3\" style=\"position:relative;\" />\n" +
                    "        <img src=\"/assets/images/PlayingCards/" + pokerService.getImage(new Card(arrayOne[3])) + ".png\" height=\"80\" width=\"50\" id=\"card4\" style=\"position:relative;\" />\n" +
                    "        <img src=\"/assets/images/PlayingCards/" + pokerService.getImage(new Card(arrayOne[4])) + ".png\" height=\"80\" width=\"50\" id=\"card5\" style=\"position:relative;\" /></td>";

            text += "<td>" + usersInGame.get(j).getG().getDateOfGame() + "</td>";
            text += "</tr>";

        }
        text += "</table>";

        String username = context.getSession().get("username");
        int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();
        result.render("register", username)
                .render("money", playerMoney)
                .render("result",text);

        return result;
    }

    public Result lobbyHost(Context context, FlashScope flashScope) {
        Result result = Results.html();

        String username = context.getSession().get("username");
        String gameName = context.getParameter("gameName");
        int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();
        int costToPlay = context.getParameterAsInteger("buyInAmount");

        if (costToPlay > playerMoney) {
            flashScope.error("You do not have enough funds to host a game that cost R " + costToPlay);
            return result.redirect(router.getReverseRoute(ApplicationController.class, "multiplayer"));
        }
        if (costToPlay < 100 || costToPlay > 10000) {
            flashScope.error("Please choose an amount between R100 and R10000");
            return result.redirect(router.getReverseRoute(ApplicationController.class, "multiplayer"));
        }

        List<Game> gameListExists = multiplayerService.gameGet(gameName);

        if (gameListExists == null || gameListExists.size() == 0) {
            Game service = new Game();
            service.setDateOfGame(new Date());
            service.setGameName(gameName);
            service.setActive(true);
            service.setHost(context.getSession().get("username"));
            service.setCostToPlay(costToPlay);
            multiplayerService.gameStore(service);
        }

        result.redirect("/lobby/" + gameName);
        return result;
    }

    public Result lobby(@PathParam("gameName") String gameName, Context context, FlashScope flashScope) {
        Result result = Results.html();

        List<Game> gameListExists = multiplayerService.gameGet(gameName);

        if (gameName != null) {
            String username = context.getSession().get("username");
            int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();

            pokerService.createDeck();

            List<UserGame> ugl = userGameService.UserGameGameGet(gameName, context.getSession().get("username"));

            if (ugl.size() == 0) {


                List<User> usersList = registerService.userGetUs(context.getSession().get("username"));

                User user = usersList.get(0);

                List<Game> gameList = multiplayerService.gameGet(gameName);

                Game service = gameList.get(0);

                if (service.getCostToPlay() > playerMoney) {
                    flashScope.error("You do not have enough funds to play a game that cost R "
                                     + service.getCostToPlay());
                    return result.redirect(router.getReverseRoute(ApplicationController.class, "multiplayer"));
                }

                UserGame userService = new UserGame();

                userService.setUsername(user.getUsername());
                userService.setGameName(service.getGameName());
                String hand = pokerService.test();

                userService.setHand(hand);
                user.addGame(service);
                userService.setU(user);
                service.addUser(user);
                userService.setG(service);
                
                userGameService.userGameStore(userService);
            }

            List<UserGame> gamesList = userGameService.UserGameGet(gameName);

            String text = "";
            for (UserGame UserGame : gamesList) {

                text += "<br><div class=\"col-lg-6\" style=\"background:#F2F2F2; border-radius:10px;\">";

                text += "<font size=\"4\">" + UserGame.getUsername() + "</font>";

                if (UserGame.getUsername().compareTo(UserGame.getG().getHost()) == 0) {
                    text += "<button type=\"button\" class=\"btn btn-success\" style=\"float: right;\"><a style=\"text-decoration:none; color: #FFFFFF;\" href=\"/gameResults/" + UserGame.getGameName() + "\">Start Game</a></button>";
                }

                text += "</div>";
            }


            result.render("register", username)
                    .render("money", playerMoney)
                    .render("gameName", gameName)
                    .render("players",text);
            return result;
        }

        return result;
    }

    public Result multiplayer(Context context) {
        Result result = Results.html();

        String username = context.getSession().get("username");
        List<Game> gamesList = multiplayerService.getAllActiveGames();
        gamesList = gamesList.stream()
                             .filter(g -> g.getHost().compareTo(username) != 0)
                             .collect(Collectors.toList());

        int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();
        result.render("register", username)
                .render("money", playerMoney)
                .render("availableGames", gamesList);
        return result;
    }

    public Result history(Context context) {

        List<UserGame> uGame = userGameService.getAllUserGames();

        String text = "";


        List<UserGame> listOfGames = userGameService.getListOfGames();
        String listOfGamesString = userGameService.getListOfGames().toString();

        listOfGamesString = listOfGamesString.replace("[", "");
        listOfGamesString = listOfGamesString.replace("]", "");
        String[] array = listOfGamesString.split(", ");

        for (int i=0;i<array.length;i++) {

            boolean flag = false;

            for (UserGame userGame : uGame) {

                if (array[i].toString().compareTo(userGame.getGameName().toString()) == 0 && !flag) {

                    flag = true;

                    text += "<table id=\"tableGames\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">" +
                            "<thead>" +
                            "<tr><th>" + array[i].toString() + "</th></tr>" +
                            "        <tr>" +
                            "        <th>Username</th>" +
                            "        <th>Hand</th>" +
                            "        <th>Date</th>" +
                            "        </tr>" +
                            "        </thead>";

                    List<UserGame> usersInGame = userGameService.UserGameGet(array[i].toString());

                    for (int j = 0; j < usersInGame.size(); j++) {


                        String uig = usersInGame.get(j).getHand();
                        uig = uig.replace("(","");
                        uig = uig.replace(")","");
                        String[] arrayOne = uig.split(",");

                        text += "<tr>";
                        text += "<td>" + usersInGame.get(j).getUsername() + "</td>";
                        text += "<td>" + "<img src=\"/assets/images/PlayingCards/"+pokerService.getImage(new Card(arrayOne[0]))+".png\" height=\"80\" width=\"50\" id=\"card1\" style=\"position:relative;\" />\n" +
                                "        <img src=\"/assets/images/PlayingCards/"+pokerService.getImage(new Card(arrayOne[1]))+".png\" height=\"80\" width=\"50\" id=\"card2\" style=\"position:relative;\" />\n" +
                                "        <img src=\"/assets/images/PlayingCards/"+pokerService.getImage(new Card(arrayOne[2]))+".png\" height=\"80\" width=\"50\" id=\"card3\" style=\"position:relative;\" />\n" +
                                "        <img src=\"/assets/images/PlayingCards/"+pokerService.getImage(new Card(arrayOne[3]))+".png\" height=\"80\" width=\"50\" id=\"card4\" style=\"position:relative;\" />\n" +
                                "        <img src=\"/assets/images/PlayingCards/"+pokerService.getImage(new Card(arrayOne[4]))+".png\" height=\"80\" width=\"50\" id=\"card5\" style=\"position:relative;\" /></td>";

                        text += "<td>" + usersInGame.get(j).getG().getDateOfGame() + "</td>";
                        text += "</tr>";

                    }
                    text += "</table>";
                }
            }
        }

        Result result = Results.html();
        String username = context.getSession().get("username");
        int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();
        result.render("register", username)
                .render("money", playerMoney)
                .render("tableData",text);


        return result;
    }

    public Result index(Context context) {

        Result result = Results.html();

        if (context.getParameter("usernameReg") == null && context.getParameter("passwordReg") == null) {
            String username = context.getParameter("username");
            String password = context.getParameter("password");

            if (!registerService.userGet(username)) {
                result = Results.redirect("/login");
                return result;
            }
            else {
                List<User> listUsers = registerService.userGetUs(username);
                User user = listUsers.get(0);

                if (user.getPassword().compareTo(password) == 0) {
                    context.getSession().put("username", username);
                }
                else {
                    result = Results.redirect("/login");
                    return result;
                }
            }

        }
        else {
            String username = context.getParameter("usernameReg");
            String password = context.getParameter("passwordReg");

            User u = new User(username, password);

            if (!registerService.userStore(u)) {
                result = Results.redirect("/register");
                return result;
            }

            result = Results.redirect("/login");
            return result;
        }


        if (context.getSession().get("username") != null) {
            String username = context.getSession().get("username");
            int playerMoney = registerService.userGetUs(username).stream().findFirst().get().getMoney();
            result.render("register", username);
            result.render("money", playerMoney);
            pokerService.createDeck();
            result.render("evaluate", pokerService.test());
            result.render("card1", pokerService.getImage(pokerService.getHandList().get(0)));
            result.render("card2", pokerService.getImage(pokerService.getHandList().get(1)));
            result.render("card3", pokerService.getImage(pokerService.getHandList().get(2)));
            result.render("card4", pokerService.getImage(pokerService.getHandList().get(3)));
            result.render("card5", pokerService.getImage(pokerService.getHandList().get(4)));
        }

        return result;

    }

    public Result login()
    {
        Result result = Results.html();
        return result;
    }

    public Result register() {
        Result result = Results.html();
        result = login();
        return result;
    }

    public Result logout(Context context)
    {
        Result result = Results.redirect("/login");
        context.getSession().clear();
        return result;
    }

    public static class SimplePojo {

        public String content;

    }
}

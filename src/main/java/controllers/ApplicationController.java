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
import com.google.inject.persist.Transactional;
import ninja.Result;
import ninja.Results;

import ninja.params.PathParam;
import ninja.session.Session;

import com.google.inject.Singleton;
import org.eclipse.jetty.security.LoginService;
import services.*;

import java.lang.Object;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Popup;

import ninja.Context;

import javax.swing.*;
import javax.swing.text.html.HTML;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;


@Singleton
public class ApplicationController {

    @Inject private PokerService pokerService;
    @Inject private RegisterService registerService;
    @Inject private loginService _loginService;
    @Inject private Session session;
    @Inject private multiplayerService _multiplayerService;
    @Inject private userGameService _userGameService;


    public Result lobbyHost(Context context) {

        Result result = Results.html();

        String hostName = context.getParameter("hostName");

        result.redirect("/lobby/" + hostName);

        return result;
    }

    public Result lobby(@PathParam("gameName") String gameName, Context context) {
        Result result = Results.html();

        List<Game> gameListExists = _multiplayerService.gameGet(gameName);

        if (gameListExists == null || gameListExists.size() == 0) {
            Game service = new Game();
            service.setDateOfGame(new Date());
            service.setGameName(gameName);
            service.setActive(true);
            service.setHost(context.getSession().get("username"));
            _multiplayerService.gameStore(service);
        }

        if (gameName != null) {
            pokerService.createDeck();

            /*User user = new User();
            user.setUsername("Andre7");
            user.setPassword("andre7");*/

            List<User> usersList = registerService.userGetUs(context.getSession().get("username"));

            User user = usersList.get(0);

            List<Game> gameList = _multiplayerService.gameGet(gameName);

            Game service = gameList.get(0);

            UserGame userService = new UserGame();

            userService.setUsername(user.getUsername());
            userService.setGameName(service.getGameName());
            String hand = pokerService.test();

            userService.setHand(hand);
            user.addGame(service);
            userService.setU(user);
            service.addUser(user);
            userService.setG(service);

           // registerService.userStore(user);
            _userGameService.userGameStore(userService);

            List<UserGame> gamesList = _userGameService.UserGameGet(gameName);

            String text = "";
            for (UserGame UserGame : gamesList) {


                text += "<br><div class=\"col-lg-6\" style=\"background:#F2F2F2; border-radius:10px;\">";

                text += "<font size=\"4\">" + UserGame.getUsername() + "</font>";
                text += "</div>";
            }

            result.render("register", context.getSession().get("username"))
                    .render("players",text);
            return result;
        }

        return result;
    }

    public Result multiplayer(Context context) {
        Result result = Results.html();

        /*User user = new User();
        user.setUsername("ssssss");
        user.setPassword("aaaaaa");

        Game service = new Game();
        service.setDateOfGame(new Date());
        service.setGameName("Lekker");
        service.setActive(true);
        UserGame userService = new UserGame();

        userService.setUsername(user.getUsername());
        userService.setGameName(service.getGameName());
        userService.setHand(pokerService.test());
        user.addGame(service);
        userService.setU(user);
        service.addUser(user);
        userService.setG(service);

        registerService.userStore(user);
        _multiplayerService.gameStore(service);
        _userGameService.userGameStore(userService);

        User user1 = new User();
        user1.setUsername("bbbbb");
        user1.setPassword("cccccc");

        UserGame userService1 = new UserGame();

        userService1.setUsername(user1.getUsername());
        userService1.setGameName(service.getGameName());
        userService1.setHand(pokerService.test());
        user1.addGame(service);
        userService1.setU(user1);
        service.addUser(user1);
        userService1.setG(service);

        registerService.userStore(user1);
        _userGameService.userGameStore(userService1);

        User user2 = new User();
        user2.setUsername("Arno");
        user2.setPassword("aaaaaaaaaa");

        Game service2 = new Game();
        service2.setDateOfGame(new Date());
        service2.setGameName("Lekkerste");
        service.setActive(false);
        UserGame userService2 = new UserGame();

        userService2.setUsername(user2.getUsername());
        userService2.setGameName(service2.getGameName());
        userService2.setHand(pokerService.test());
        user2.addGame(service2);
        userService2.setU(user2);
        service2.addUser(user2);
        userService2.setG(service2);

        registerService.userStore(user2);
        _multiplayerService.gameStore(service2);
        _userGameService.userGameStore(userService2);*/


        /*pokerService.createDeck();
        result.render("one", pokerService.test())
            .render("two", pokerService.test())
            .render("three", pokerService.test())
            .render("four", pokerService.test())
            .render("five", pokerService.test())
            .render("winHand", pokerService.evaluateHands());
        result.render("register", context.getSession().get("username"));*/

        List<Game> gamesList = _multiplayerService.getAllActiveGames();

        String text = "";

        text += "<div class=\"container\">\n" +
                "        <div class=\"col-lg-3\" style=\"background:#F9F9F9; border-radius:10px;\">\n" +
                "            <h1 class=\"text-primary\" align=\"center\">Host Game</h1>\n" +
                "            <div>\n" +
                "                <form action = \"lobbyHost\" method=\"post\">\n" +
                "                    Game Name: <br>\n" +
                "                    <input type = \"text\" name = \"hostName\" id = \"hostName\" class=\"form-control\" required > <br>\n" +
                "                    <input type = \"submit\" class=\"btn btn-success btn-block\" value=\"Host Game\">\n" +
                "                </form>\n" +
                "            </div>\n" +
                "            <br>\n" +
                "            <br>\n" +
                "        </div>\n" +
                "    </div>\n";

        for (Game game : gamesList) {


            text += "<br><div class=\"col-lg-6\" style=\"background:#F2F2F2; border-radius:10px;\">";

            text += "<font size=\"4\">" + game.getGameName() + "</font>";
            text += "<button type=\"button\" class=\"btn btn-success\" style=\"float: right;\"><a style=\"text-decoration:none; color: #FFFFFF;\" href=\"/lobby/"+game.getGameName()+"\">Join</a></button>";

            text += "</div>";
        }

        result.render("register", context.getSession().get("username"))
                .render("availableGames",text);
        return result;
    }

    public Result history(Context context) {

        List<UserGame> uGame = _userGameService.getAllUserGames();

        String text = "";


        List<UserGame> listOfGames = _userGameService.getListOfGames();
        String listOfGamesString = _userGameService.getListOfGames().toString();

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

                    List<UserGame> usersInGame = _userGameService.UserGameGet(array[i].toString());

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
        result.render("register", context.getSession().get("username"))
                .render("tableData",text);


        return result;
    }

    public Result index(Context context) {

        Result result = Results.html();

        if (context.getParameter("usernameReg") == null && context.getParameter("passwordReg") == null)
        {
            String name = context.getParameter("username");
            String pass = context.getParameter("password");
            if (!registerService.userGet(name)) {
                result = Results.redirect("/register");
                return result;
            }

            context.getSession().put("username", name);
        }
        else {
            String name = context.getParameter("usernameReg");
            String pass = context.getParameter("passwordReg");
            User u = new User(name, pass);
            if (!registerService.userStore(u)) {
                result = Results.redirect("/register");
                return result;
            }

            result = Results.redirect("/login");
            return result;
        }


        result.render("register", context.getSession().get("username"));
        System.out.println("ssssssssssssssssssssssssssssssssssssssssssssss: " + context.getSession().get("username"));

        pokerService.createDeck();
        result.render("evaluate", pokerService.test());
        result.render("card1", pokerService.getImage(pokerService.getHandList().get(0)));
        result.render("card2", pokerService.getImage(pokerService.getHandList().get(1)));
        result.render("card3", pokerService.getImage(pokerService.getHandList().get(2)));
        result.render("card4", pokerService.getImage(pokerService.getHandList().get(3)));
        result.render("card5", pokerService.getImage(pokerService.getHandList().get(4)));
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

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
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import ninja.Result;
import ninja.Results;

import ninja.session.Session;

import com.google.inject.Singleton;
import org.eclipse.jetty.security.LoginService;
import services.PokerService;
import services.RegisterService;
import services.loginService;

import java.lang.Object;
import java.util.Date;
import javax.swing.Popup;

import ninja.Context;
import services.multiplayerService;

import javax.swing.*;


@Singleton
public class ApplicationController {

    @Inject private PokerService pokerService;
    @Inject private RegisterService registerService;
    @Inject private loginService _loginService;
    @Inject private Session session;
    @Inject private multiplayerService _multiplayerService;

    public Result multiplayer() {

        _multiplayerService.gameStore(new Game("NuweGame", new Date()));

        Result result = Results.html();
        pokerService.createDeck();
        result.render("one", pokerService.test())
            .render("two", pokerService.test())
            .render("three", pokerService.test())
            .render("four", pokerService.test())
            .render("five", pokerService.test())
            .render("winHand", pokerService.evaluateHands());
        return result;
    }

    public Result history() {
        Result result = Results.html();
        return result;
    }

    public Result index(Context context) {

        Result result = Results.html();
        String names = "";
        //boolean hello = registerService.userGet();
        if (context.getParameter("usernameReg") == null && context.getParameter("passwordReg") == null)
        {
            String name = context.getParameter("username");
            String pass = context.getParameter("password");
            if (!registerService.userGet(name)) {
                result = Results.redirect("/register");
                System.out.println("already");
                return result;
            }
            names = name;
            //session.put("username", names);
            context.getSession().put("username", names);

        }
        else {
            String name = context.getParameter("usernameReg");
            String pass = context.getParameter("passwordReg");
            User u = new User(name,pass);
            if (!registerService.userStore(u))
            {
                result = Results.redirect("/register");
                System.out.println("No user found");
                return result;
            }
            names = name;

            result = Results.redirect("/register");
            return result;
        }
        session = context.getSession();
        c = context;
        logged = names;


        result.render("register", "Hello " + context.getSession().get("username"));
        result.render("name", pokerService.test());
        result.render("card1", pokerService.getImage(pokerService.getHandList().get(0)));
        result.render("card2", pokerService.getImage(pokerService.getHandList().get(1)));
        result.render("card3", pokerService.getImage(pokerService.getHandList().get(2)));
        result.render("card4", pokerService.getImage(pokerService.getHandList().get(3)));
        result.render("card5", pokerService.getImage(pokerService.getHandList().get(4)));
        return result;

    }
    private String logged = "";
    Context c = null;
    public Result login()
    {
        Result result = Results.html();
        /*if (c != null && c.getSession().get("username") != null
                && logged.
                compareTo("") != 0
                && c.getSession().
                get("username").
                compareTo(logged) ==0) {
            System.out.println("HEKEKEKEKEK");
            return result.redirect("/index");
        }*/

        //result.render("login", loginService.output());
        return result;
    }

    public Result register()
    {
        registerService.getAllUsers();
        Result result = Results.html();
        result = login();
        return result;
    }

    public Result logout(Context context)
    {
        Result result = Results.redirect("/login");
        c.getSession().clear();
        session = null;
        context.getSession().clear();
        return result;
    }
    
    public static class SimplePojo {

        public String content;
        
    }
}

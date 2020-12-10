package com.jakpop.stepsdictionary.views.login;

import com.jakpop.stepsdictionary.data.service.AuthService;
import com.jakpop.stepsdictionary.views.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
public class LoginView extends Div {

    public LoginView(AuthService authService) {
        setId("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        add(
                new H1("Welcome"),
                username,
                password,
                new Button("Login", event -> {
                    try {
                        authService.authenticate(username.getValue(), password.getValue());
                        UI.getCurrent().navigate("about");
                    } catch (AuthService.AuthException e) {
                        Notification.show("Wrong credentials");
                    }
                }),
                new Button("Register", event -> {
                    try {
                        authService.create(username.getValue(), password.getValue());
                        UI.getCurrent().navigate("login");
                        Notification.show("User created");
                    } catch (AuthService.AuthException e) {
                        Notification.show("User already exists");
                    }
                })
        );
    }

}

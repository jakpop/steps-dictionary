package com.jakpop.stepsdictionary.views.login;

import com.jakpop.stepsdictionary.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
@RouteAlias(value = "")
public class LoginView extends Div {

    public LoginView() {
        setId("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        add(
                new H1("Welcome"),
                username,
                password,
                new Button("Login")
        );
    }

}

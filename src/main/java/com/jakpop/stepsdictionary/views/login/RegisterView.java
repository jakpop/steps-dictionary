package com.jakpop.stepsdictionary.views.login;

import com.jakpop.stepsdictionary.data.service.AuthService;
import com.jakpop.stepsdictionary.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "register", layout = MainView.class)
@PageTitle("Register")
@CssImport("./styles/views/login/register-view.css")
public class RegisterView extends Composite {

    private final AuthService authService;

    public RegisterView(AuthService authService) {
        this.authService = authService;
        setId("register-view");
    }

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField confirm_password = new PasswordField("Confirm password");

        return new VerticalLayout(
                username,
                password,
                confirm_password,
                new Button("Send", event -> register(
                        username.getValue(),
                        password.getValue(),
                        confirm_password.getValue()
                ))
        );
    }

    private void register(String username, String password, String confirm_password) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (password.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password.equals(confirm_password)) {
            Notification.show("Passwords don't match");
        } else {
            authService.register(username, password);
            Notification.show("Registration succeeded");
            UI.getCurrent().navigate("login");
        }
    }
}

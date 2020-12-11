package com.jakpop.stepsdictionary.data.service;

import com.jakpop.stepsdictionary.data.entity.enums.Role;
import com.jakpop.stepsdictionary.data.entity.users.User;
import com.jakpop.stepsdictionary.views.about.AboutView;
import com.jakpop.stepsdictionary.views.dancehall.DancehallView;
import com.jakpop.stepsdictionary.views.hiphop.HipHopView;
import com.jakpop.stepsdictionary.views.logout.LogoutView;
import com.jakpop.stepsdictionary.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    public record AuthorizedRoute(String route, String name, Class<?extends Component> view) {

    }

    public class AuthException extends Exception {
    }

    private final UserRepository userRepository;

    public void create(String username, String password) throws AuthException {
        User byUsername = userRepository.getByUsername(username);
        if (byUsername != null) {
            throw new AuthException();
        }
        User user = new User(username, password, Role.USER);
        userRepository.insert(user);
    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userRepository.getByUsername(username);
        if (user != null && user.checkPassword(password)) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream().forEach(route ->
                RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainView.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        ArrayList<AuthorizedRoute> routes = new ArrayList<>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("about", "About", AboutView.class));
            routes.add(new AuthorizedRoute("steps/dancehall", "Dancehall Steps", DancehallView.class));
            routes.add(new AuthorizedRoute("steps/hiphop", "Hip Hop Steps", HipHopView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("about", "About", AboutView.class));
            routes.add(new AuthorizedRoute("steps/dancehall", "Dancehall Steps", DancehallView.class));
            routes.add(new AuthorizedRoute("steps/hiphop", "Hip Hop Steps", HipHopView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }

        return routes;
    }
}

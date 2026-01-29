package com.fileinnout.global;

import com.fileinnout.domain.user.UserController;
import com.fileinnout.domain.user.UserRepository;
import com.fileinnout.domain.user.UserService;
import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {
    HikariDataSource ds = new HikariDataSource();
    private static final Map<String, Controller> controllerMap = new HashMap<>();
    UserRepository userRepository = new UserRepository(ds);
    UserService userService = new UserService(userRepository);
    UserController userController = new UserController(userService);
    public AppConfig() {
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://192.168.92.100:3306/test");
        ds.setUsername("lee");
        ds.setPassword("qwer1234");
        controllerMap.put("/user/signup", userController);
    }

    public Controller getController(String uri) {
        return controllerMap.get(uri);
    }
}

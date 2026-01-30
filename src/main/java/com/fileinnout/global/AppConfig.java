package com.fileinnout.global;

import com.fileinnout.domain.Editor.EditorController;
import com.fileinnout.domain.Editor.EditorRepository;
import com.fileinnout.domain.Editor.EditorService;
import com.fileinnout.domain.user.controller.UserLoginController;
import com.fileinnout.domain.user.controller.UserSignupController;
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
    UserSignupController userSignupController = new UserSignupController(userService);
    UserLoginController userLoginController = new UserLoginController(userService);
    EditorRepository editorRepository = new EditorRepository(ds);
    EditorService editorService = new EditorService(editorRepository);
    EditorController editorController = new EditorController(editorService);
    public AppConfig() {
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://192.168.92.100:3306/test");
        ds.setUsername("lee");
        ds.setPassword("qwer1234");
        controllerMap.put("/user/signup", userSignupController);
        controllerMap.put("/user/login", userLoginController);

        controllerMap.put("/editor/create", editorController);
        controllerMap.put("/view", editorController);
        controllerMap.put("/editor/edit", editorController);
        controllerMap.put("/editor/delete", editorController);
        controllerMap.put("/editor/save", editorController);
        controllerMap.put("/editor/permission", editorController);
    }

    public Controller getController(String uri) {
        return controllerMap.get(uri);
    }
}

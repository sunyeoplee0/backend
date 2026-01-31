package com.fileinnout.global;

import com.fileinnout.domain.Editor.EditorController;
import com.fileinnout.domain.Editor.EditorRepository;
import com.fileinnout.domain.Editor.EditorService;
import com.fileinnout.domain.chat.controller.ChatMessageController;
import com.fileinnout.domain.chat.controller.ChatRoomController;
import com.fileinnout.domain.chat.ChatMessageRepository;
import com.fileinnout.domain.chat.ChatRoomRepository;
import com.fileinnout.domain.chat.ChatService;
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

    ChatMessageRepository chatMessageRepository = new ChatMessageRepository(ds);
    ChatRoomRepository chatRoomRepository = new ChatRoomRepository(ds);
    ChatService chatService = new ChatService(chatMessageRepository, chatRoomRepository);
    ChatRoomController chatRoomController = new ChatRoomController(chatService);
    ChatMessageController chatMessageController = new ChatMessageController(chatService);

    public AppConfig() {
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://10.10.10.11:3306/test");
        ds.setUsername("kjh");
        ds.setPassword("qwer1234");

        controllerMap.put("/user/signup", userSignupController);
        controllerMap.put("/user/login", userLoginController);

        controllerMap.put("/editor/create", editorController);
        controllerMap.put("/view", editorController);
        controllerMap.put("/editor/edit", editorController);
        controllerMap.put("/editor/delete", editorController);
        controllerMap.put("/editor/save", editorController);
        controllerMap.put("/editor/permission", editorController);
        controllerMap.put("/chatroom/create", chatRoomController);
        controllerMap.put("/chatroom/list", chatRoomController);
        controllerMap.put("/chat/read", chatMessageController);
        controllerMap.put("/chat/send", chatMessageController);

    }

    public Controller getController(String uri) {
        return controllerMap.get(uri);
    }
}

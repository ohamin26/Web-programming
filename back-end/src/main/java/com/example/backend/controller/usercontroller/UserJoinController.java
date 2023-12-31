package com.example.backend.controller.usercontroller;

import com.example.backend.controller.Controller;
import com.example.backend.dao.UserDao;
import com.example.backend.json.JsonParsing;
import com.example.backend.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

public class UserJoinController implements Controller {
    UserDao userDao ;
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();

        //리스너로 넣어둔 DB conn
        ServletContext sc = request.getServletContext();
        Connection conn = (Connection)sc.getAttribute("DBConnection");
        //DAO 호출
        userDao = new UserDao(conn);
        //request Json 매핑
        Map<String, String> jsonMap = JsonParsing.parsing(request);
        //user 매핑
        user.setUserId(jsonMap.get("userId"));
        user.setPassword(jsonMap.get("password"));
        user.setName(jsonMap.get("name"));
        user.setPhoneNumber(jsonMap.get("phoneNumber"));
        user.setNickname(jsonMap.get("nickname"));
        user.setSchool_id(Integer.valueOf(jsonMap.get("school_id")));
        user.setMajor_id(Integer.valueOf(jsonMap.get("major_id")));
        //insert 성공시 1 , 실패시 0
        int querySuccessCheck = userDao.join(user);

        //Json 으로 리턴
        response.getWriter().write("{\"querySuccessCheck\" : \"" + querySuccessCheck + "\"}");
    }
}

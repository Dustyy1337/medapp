package com.medstat.medapp;

import com.medstat.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainScreen {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String mainScreen(HttpSession session){
        if(session.getAttribute("username")==null) return "redirect:/login";
        else return "redirect:/dashboard";
    }
    @GetMapping("/login")
    public String loginScreen(Model model, HttpSession session) {
        if(session.getAttribute("username")!=null) return "redirect:/dashboard";
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginAuth(@ModelAttribute("user") User user, Model model, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        String sql = "SELECT users.username AS username, users.firstname AS firstname, users.lastname AS lastname, usertypes.usertype AS usertype FROM users INNER JOIN usertypes ON users.usertype_id = usertypes.usertype_id WHERE users.username = ? AND users.password = ?";
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql, username, password);
        if(resultSet.isEmpty()) return "redirect:/login";
        session.setAttribute("username", user.getUsername());
        session.setAttribute("firstname", resultSet.get(0).get("firstname"));
        session.setAttribute("lastname", resultSet.get(0).get("lastname"));
        session.setAttribute("usertype", resultSet.get(0).get("usertype"));
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if(session.getAttribute("username")==null) return "redirect:/login";
        return "dashboard";
    }
}

package com.medstat.medapp;

import com.medstat.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainScreen {
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
        //login auth

        session.setAttribute("username", user.getUsername());
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if(session.isNew()) return "redirect:/login";
        return "dashboard";
    }
}

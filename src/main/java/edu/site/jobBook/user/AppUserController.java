package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());

        // Log user activity
        UserActivity activity = UserActivity.builder()
                .userId(user.getId())
                .activity("Viewed profile")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        userActivityService.saveUserActivity(activity);

        // Retrieve user activities
        List<UserActivity> activities = userActivityService.getUserActivities(user.getId());
        model.addAttribute("activities", activities);

        return "profile";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String roles, Model model) {
        try {
            userService.createUser(username, password, new HashSet<>(Arrays.asList(roles.split(","))));
            model.addAttribute("success", "User registered successfully!");
            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin";
        }
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String username, Model model) {
        try {
            userService.deleteUserByUsername(username);
            model.addAttribute("success", "User deleted successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String showAdminHomePage(Model model) {
        List<AppUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }
}

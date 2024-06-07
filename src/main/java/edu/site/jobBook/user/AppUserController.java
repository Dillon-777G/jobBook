package edu.site.jobBook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyService;

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

    @Autowired
    private CompanyService companyService;

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());

        UserActivity activity = UserActivity.builder()
                .userId(user.getId())
                .activity("Viewed profile")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        userActivityService.saveUserActivity(activity);

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

            // Log admin activity
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser adminUser = (AppUser) authentication.getPrincipal();
            UserActivity activity = UserActivity.builder()
                    .userId(adminUser.getId())
                    .activity("Registered new user: " + username)
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
            userActivityService.saveUserActivity(activity);

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

            // Log admin activity
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser adminUser = (AppUser) authentication.getPrincipal();
            UserActivity activity = UserActivity.builder()
                    .userId(adminUser.getId())
                    .activity("Deleted user: " + username)
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
            userActivityService.saveUserActivity(activity);

            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin";
        }
    }

    @PostMapping("/createCompany")
    public String createCompany(@RequestParam String name, @RequestParam String description, Model model) {
        try {
            Company company = new Company();
            company.setName(name);
            company.setDescription(description);
            companyService.addCompany(company);
            model.addAttribute("success", "Company created successfully!");

            // Log admin activity
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser adminUser = (AppUser) authentication.getPrincipal();
            UserActivity activity = UserActivity.builder()
                    .userId(adminUser.getId())
                    .activity("Created new company: " + name)
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
            userActivityService.saveUserActivity(activity);

            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin";
        }
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(@RequestParam Long companyId, Model model) {
        try {
            companyService.deleteCompany(companyId);
            model.addAttribute("success", "Company deleted successfully!");

            // Log admin activity
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser adminUser = (AppUser) authentication.getPrincipal();
            UserActivity activity = UserActivity.builder()
                    .userId(adminUser.getId())
                    .activity("Deleted company with ID: " + companyId)
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
            userActivityService.saveUserActivity(activity);

            return "redirect:/admin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin";
        }
    }

    @GetMapping("/admin")
    public String showAdminHomePage(Model model) {
        List<AppUser> users = userService.findAllUsers();
        List<UserActivity> activities = userActivityService.getAllUserActivities();
        List<Company> companies = companyService.findAllCompanies();  // Get all companies
        List<UserSession> sessions = userService.getAllActiveSessions();
        model.addAttribute("users", users);
        model.addAttribute("activities", activities);
        model.addAttribute("companies", companies);
        model.addAttribute("sessions", sessions);
        return "admin";
    }
}

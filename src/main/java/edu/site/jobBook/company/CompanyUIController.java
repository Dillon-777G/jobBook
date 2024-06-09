package edu.site.jobBook.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusService;

import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserActivity;
import edu.site.jobBook.user.UserActivityService;

// pagination imports
import org.springframework.data.domain.Page;    
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;



@Controller
public class CompanyUIController {

    private final UserActivityService userActivityService;
    private final CompanyService companyService;
    private final HiringStatusService hiringStatusService;

    @Autowired
    public CompanyUIController(CompanyService companyService, HiringStatusService hiringStatusService, UserActivityService userActivityService) {
        this.companyService = companyService;
        this.hiringStatusService = hiringStatusService;
        this.userActivityService = userActivityService;
    }

    @GetMapping("/companies-page")
    public String companyPage2(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        int pageSize = 5; // <== Page size for pagination
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Company> companies = companyService.findAllCompaniesPage(pageable); // <== Get paginated companies

        Map<Long, String> hiringStatusMap = new HashMap<>(); // <== Map to store hiring status
        companies.forEach(company -> {
            HiringStatus status = hiringStatusService.getHiringStatus(company.getId());
            if (status != null) {
                hiringStatusMap.put(company.getId(), status.name());
            } else {
                hiringStatusMap.put(company.getId(), "UNKNOWN");
            }
        });

        Map<String, String> hiringStatusDescriptions = new HashMap<>(); // <== Descriptions for hiring statuses
        hiringStatusDescriptions.put("HIRING", "Hiring");
        hiringStatusDescriptions.put("NOT_HIRING", "Not Hiring");
        hiringStatusDescriptions.put("UNKNOWN", "Unknown");

        model.addAttribute("companies", companies);
        model.addAttribute("hiringStatusMap", hiringStatusMap);
        model.addAttribute("hiringStatusDescriptions", hiringStatusDescriptions);

        userActivityService.saveUserActivity(UserActivity.builder()
            .userId(user.getId())
            .activity("Viewed company listing page")
            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .build());

        return "companies-list";
    }

    @GetMapping("/company-details-panel/{id}")
    public String companyDetailsPane(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        Optional<Company> companyOpt = companyService.findCompanyById(id);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            model.addAttribute("company", company);
            model.addAttribute("jobs", companyService.findJobsByCompanyId(id));

            userActivityService.saveUserActivity(UserActivity.builder()
                .userId(user.getId())
                .activity("Viewed company listing detail")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

            return "company-right-panel";
        } else {
            return "redirect:/companies-page";
        }
    }

    @GetMapping("/company/{id}")
    public String companyDetails(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        UserActivity activity = UserActivity.builder()
                .userId(user.getId())
                .activity("Viewed company details for company ID: " + id)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        userActivityService.saveUserActivity(activity);

        Optional<Company> company = companyService.findCompanyById(id);
        if (company.isPresent()) {
            HiringStatus status = hiringStatusService.getHiringStatus(id); // <== Get hiring status for the company
            model.addAttribute("company", company.get());
            model.addAttribute("jobs", companyService.findJobsByCompanyId(id)); // <== Add jobs to the model
            model.addAttribute("posts", companyService.findPostsByCompanyId(id)); // <== Add posts to the model
            model.addAttribute("hiringStatus", status);

            Map<String, String> hiringStatusDescriptions = new HashMap<>(); // <== Descriptions for hiring statuses
            hiringStatusDescriptions.put("HIRING", "Hiring");
            hiringStatusDescriptions.put("NOT_HIRING", "Not Hiring");
            hiringStatusDescriptions.put("UNKNOWN", "Unknown");
            model.addAttribute("hiringStatusDescriptions", hiringStatusDescriptions);

            return "company-details";
        } else {
            return "redirect:/companies-page";
        }
    }
}
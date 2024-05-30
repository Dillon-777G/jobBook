package edu.site.jobBook.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusService;

@Controller
public class CompanyUIController {

    private final CompanyService companyService;
    private final HiringStatusService hiringStatusService;
    @Autowired
    public CompanyUIController(CompanyService companyService, HiringStatusService hiringStatusService) {
        this.companyService = companyService;
        this.hiringStatusService = hiringStatusService;
    }

    @GetMapping("/companies-page")
    public String companyPage(Model model) {
        List<Company> companies = companyService.findAllCompanies();
        Map<Long, HiringStatus> hiringStatusMap = new HashMap<>();
        
        companies.forEach(company -> {
            HiringStatus status = hiringStatusService.getHiringStatus(company.getId());
            hiringStatusMap.put(company.getId(), status);
        });
        
        model.addAttribute("companies", companies);
        model.addAttribute("hiringStatusMap", hiringStatusMap);
        return "companies-page";
    }

    @GetMapping("/company/{id}")
    public String companyDetails(@PathVariable Long id, Model model) {
        Optional<Company> company = companyService.findCompanyById(id);
        if (company.isPresent()) {
            HiringStatus status = hiringStatusService.getHiringStatus(id);
            model.addAttribute("company", company.get());
            model.addAttribute("jobs", companyService.findJobsByCompanyId(id));
            model.addAttribute("posts", companyService.findPostsByCompanyId(id));
            model.addAttribute("hiringStatus", status);
            return "company-details";
        } else {
            return "redirect:/companies-page";  // Redirect to the company list page if the company is not found
        }
    }
    
}

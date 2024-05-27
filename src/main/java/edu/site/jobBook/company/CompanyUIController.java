package edu.site.jobBook.company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CompanyUIController {

    private final CompanyService companyService;

    @Autowired
    public CompanyUIController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies-page")
    public String companyPage(Model model) {
        List<Company> companies = companyService.findAllCompanies();
        model.addAttribute("companies", companies);
        return "companies-page";
    }

    @GetMapping("/company/{id}")
    public String companyDetails(@PathVariable Long id, Model model) {
        Optional<Company> company = companyService.findCompanyById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            model.addAttribute("jobs", companyService.findJobsByCompanyId(id));
            model.addAttribute("posts", companyService.findPostsByCompanyId(id));
            return "company-details";
        } else {
            return "redirect:/companies-page";  // Redirect to the company list page if the company is not found
        }
    }
    
}

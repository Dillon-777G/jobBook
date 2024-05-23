package edu.site.jobBook.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyUIController {

    private final CompanyService companyService;

    @Autowired
    public CompanyUIController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/company-page")
    public String companyPage(Model model) {
        List<Company> companies = companyService.findAllCompanies();
        model.addAttribute("companies", companies);
        return "company-page";
    }
    
}

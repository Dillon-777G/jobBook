package edu.site.jobBook.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.site.jobBook.post.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company Management", description = "API for managing companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(summary = "Add a new company", description = "Creates a new company and returns the created company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        Company createdCompany = companyService.addCompany(company);
        return ResponseEntity.ok(createdCompany);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find company by ID", description = "Returns a company with the specified ID")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        return companyService.findCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name/{name}")
    @Operation(summary = "Find company by name", description = "Returns a company with the specified name")
    public ResponseEntity<Company> findCompanyByName(@PathVariable String name) {
        return companyService.findCompanyByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company", description = "Deletes the company with the specified ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.findCompanyById(id).isPresent()) {
            companyService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-partial-name/{partialName}")
    @Operation(summary = "Find companies by partial name", description = "Returns a list of companies whose names contain the given substring")
    public ResponseEntity<List<Company>> getCompaniesByPartialName(@PathVariable String partialName) {
        List<Company> companies = companyService.findCompaniesByPartialName(partialName);
        return ResponseEntity.ok(companies);
    }

    
}

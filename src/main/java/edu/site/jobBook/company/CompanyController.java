package edu.site.jobBook.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company Management", description = "API for managing companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(summary = "Add a new company", description = "Creates a new company and returns the created company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        logger.info("Request to add company: {}", company.getName());
        Company createdCompany = companyService.addCompany(company);
        return ResponseEntity.ok(createdCompany);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find company by ID", description = "Returns a company with the specified ID")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        logger.info("Request to find company by id: {}", id);
        return companyService.findCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name/{name}")
    @Operation(summary = "Find company by name", description = "Returns a company with the specified name")
    public ResponseEntity<Company> findCompanyByName(@PathVariable String name) {
        logger.info("Request to find company by name: {}", name);
        return companyService.findCompanyByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company", description = "Deletes the company with the specified ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        logger.info("Request to delete company by id: {}", id);
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
        logger.info("Request to find companies by partial name: {}", partialName);
        List<Company> companies = companyService.findCompaniesByPartialName(partialName);
        return ResponseEntity.ok(companies);
    }

    @GetMapping
    @Operation(summary = "Find all companies", description = "Returns a list of all companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        logger.info("Request to find all companies");
        List<Company> companies = companyService.findAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a company", description = "Updates the company with the specified ID")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        logger.info("Request to update company with id: {}", id);
        try {
            Company updatedCompany = companyService.updateCompany(id, company);
            return ResponseEntity.ok(updatedCompany);
        } catch (RuntimeException e) {
            logger.error("Error updating company with id: {}", id, e);
            return ResponseEntity.status(404).body(null);
        }
    }
}
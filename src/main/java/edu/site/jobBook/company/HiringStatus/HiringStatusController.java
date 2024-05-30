package edu.site.jobBook.company.HiringStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class HiringStatusController {

    @Autowired
    private HiringStatusService hiringStatusService;

    @PostMapping("/{companyId}/hiringStatus")
    public void setHiringStatus(@PathVariable long companyId, @RequestParam HiringStatus hiringStatus) {
        hiringStatusService.setHiringStatus(companyId, hiringStatus);
    }

    @GetMapping("/{companyId}/hiringStatus")
    public CompanyHiringStatus getCompanyHiringStatus(@PathVariable long companyId) {
        return hiringStatusService.getCompanyHiringStatus(companyId);
    }

    @GetMapping("/hiring")
    public List<CompanyHiringStatus> getCompaniesHiring() {
        return hiringStatusService.getCompaniesHiring();
    }
}

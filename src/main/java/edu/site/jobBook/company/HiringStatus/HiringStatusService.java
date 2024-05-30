package edu.site.jobBook.company.HiringStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiringStatusService {

    @Autowired
    private HiringStatusRepository repository;

    public void setHiringStatus(long companyId, HiringStatus hiringStatus) {
        CompanyHiringStatus status = new CompanyHiringStatus(companyId, hiringStatus);
        repository.save(status);
    }

    public CompanyHiringStatus getCompanyHiringStatus(long companyId) {
        return repository.findById(companyId).orElse(new CompanyHiringStatus(companyId, HiringStatus.UNKNOWN));
    }

    public HiringStatus getHiringStatus(long companyId) {
        return repository.findById(companyId)
                         .map(CompanyHiringStatus::getHiringStatus)
                         .orElse(HiringStatus.UNKNOWN);
    }

    public List<CompanyHiringStatus> getCompaniesHiring() {
        return repository.findByHiringStatus(HiringStatus.HIRING);
    }
}

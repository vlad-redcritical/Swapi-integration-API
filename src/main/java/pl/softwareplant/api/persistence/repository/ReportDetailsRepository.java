package pl.softwareplant.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.softwareplant.api.persistence.entity.ReportDetails;

public interface ReportDetailsRepository extends JpaRepository<ReportDetails, Long> {
}

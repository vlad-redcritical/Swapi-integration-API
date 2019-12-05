package pl.softwareplant.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.softwareplant.api.persistence.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}

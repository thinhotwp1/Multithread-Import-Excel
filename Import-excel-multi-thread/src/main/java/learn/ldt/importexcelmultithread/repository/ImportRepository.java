package learn.ldt.importexcelmultithread.repository;

import learn.ldt.importexcelmultithread.dto.DataCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<DataCommunity, Long> {
}

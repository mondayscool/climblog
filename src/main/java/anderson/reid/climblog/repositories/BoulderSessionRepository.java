package anderson.reid.climblog.repositories;

import anderson.reid.climblog.domain.BoulderSession;
import org.springframework.data.repository.CrudRepository;

public interface BoulderSessionRepository extends CrudRepository<BoulderSession, Long> {
}

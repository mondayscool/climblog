package anderson.reid.climblog.repositories;

import anderson.reid.climblog.domain.climb.Route;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<Route, Long> {
}

package anderson.reid.climblog.services.JPAservices;

import anderson.reid.climblog.domain.sessionclimb.Pitch;
import anderson.reid.climblog.exceptions.EmptyListException;
import anderson.reid.climblog.repositories.PitchRepository;
import anderson.reid.climblog.repositories.RouteRepository;
import anderson.reid.climblog.services.SessionClimbService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JPAPitchService implements SessionClimbService<Pitch> {

   private final PitchRepository pitchRepository;
   private final RouteRepository routeRepository;

   public JPAPitchService(PitchRepository pitchRepository, RouteRepository routeRepository) {
      this.pitchRepository = pitchRepository;
      this.routeRepository = routeRepository;
   }

   @Override
   public List<Pitch> getSessionClimbs() {
      List<Pitch> pitches = new ArrayList<>();
      pitchRepository.findAll().iterator().forEachRemaining(pitches::add);

      if(pitches.isEmpty()) {
         throw new EmptyListException("-- No pitches found! --", "Pitches", "/log/pitch");
      }

      Collections.sort(pitches);

      return pitches;
   }

   @Override
   public Pitch save(Pitch sessionClimb) {
      sessionClimb.getClimb().updateStatus(sessionClimb.getType());
      routeRepository.save(sessionClimb.getClimb());
      return pitchRepository.save(sessionClimb);
   }

   @Override
   public Pitch findClimbSessionById(Long id) {
      Optional<Pitch> pitch = pitchRepository.findById(id);
      return pitch.orElse(null);
   }

   @Override
   public void deleteById(Long id) {
      pitchRepository.deleteById(id);
   }
}

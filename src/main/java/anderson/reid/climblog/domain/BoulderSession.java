package anderson.reid.climblog.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "boulder_sessions")
public class BoulderSession extends SessionClimb<Boulder> {

   @Column(name = "attempts")
   private Integer attempts;

   @Column(name = "spotters")
   private String spotters;

   @Builder
   public BoulderSession(Long id, Boulder climb, LocalDate date, char type, String notes, Integer attempts, String spotters) {
      super(id, climb, date, type, notes);
      this.attempts = attempts;
      this.spotters = spotters;
   }
}

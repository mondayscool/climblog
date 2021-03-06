package anderson.reid.climblog.controllers;

import anderson.reid.climblog.domain.climb.Boulder;
import anderson.reid.climblog.domain.grade.VGrade;
import anderson.reid.climblog.exceptions.EntityNotFoundException;
import anderson.reid.climblog.services.ClimbService;
import anderson.reid.climblog.services.GradeService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/climbs/boulder")
@Controller
public class BoulderController {

   private final ClimbService<Boulder> boulderService;
   private final GradeService<VGrade> gradeService;

   public BoulderController(ClimbService<Boulder> boulderService, GradeService<VGrade> gradeService) {
      this.boulderService = boulderService;
      this.gradeService = gradeService;
   }

   @GetMapping(value = {"", "/"})
   public String listBoulders(@RequestParam(value = "exception", required = false) boolean exception, Model model) {
      model.addAttribute("boulders", boulderService.getClimbs());
      model.addAttribute("exception", exception);
      return "climbs/boulders";
   }

   @GetMapping("/create")
   public String createBoulder(Model model) {
      model.addAttribute("grades", gradeService.getGrades());
      model.addAttribute("boulder", new Boulder());
      return "create/create_boulder";
   }

   @PostMapping("/new_boulder")
   public String saveOrUpdateBoulder(@ModelAttribute Boulder boulder) {
      boulderService.save(boulder);
      return "redirect:/climbs/boulder";
   }

   @GetMapping("/{id}/edit")
   public String editBoulder(@PathVariable String id, Model model) {
      Boulder boulder = boulderService.findClimbById(Long.parseLong(id));

      if (boulder == null) {
         throw new EntityNotFoundException("Boulder", id);
      }

      model.addAttribute("boulder", boulder);
      model.addAttribute("grades", gradeService.getGrades());
      return "create/create_boulder";
   }

   @GetMapping("/{id}/delete")
   public String deleteBoulder(@PathVariable String id) {
      boulderService.deleteById(Long.parseLong(id));
      return "redirect:/climbs/boulder";
   }

   @ExceptionHandler(ConstraintViolationException.class)
   public String handleBoulderInBoulderSession() {
      return "redirect:/climbs/boulder?exception=true";
   }
}

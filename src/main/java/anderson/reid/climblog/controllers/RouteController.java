package anderson.reid.climblog.controllers;

import anderson.reid.climblog.domain.climb.Route;
import anderson.reid.climblog.domain.grade.YDSGrade;
import anderson.reid.climblog.exceptions.EntityNotFoundException;
import anderson.reid.climblog.services.ClimbService;
import anderson.reid.climblog.services.GradeService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/climbs/route")
@Controller
public class RouteController {

   private final ClimbService<Route> routeService;
   private final GradeService<YDSGrade> gradeService;

   public RouteController(ClimbService<Route> routeService, GradeService<YDSGrade> gradeService) {
      this.routeService = routeService;
      this.gradeService = gradeService;
   }

   @RequestMapping({"", "/"})
   public String listRoutes(Model model) {
      model.addAttribute("routes", routeService.getClimbs());
      return "climbs/routes";
   }

   @RequestMapping("/create")
   public String createRoute(Model model) {
      model.addAttribute("grades", gradeService.getGrades());
      model.addAttribute("route", new Route());
      return "create/create_route";
   }

   @PostMapping("/new_route")
   public String saveOrUpdateRoute(@ModelAttribute Route route) {
      routeService.save(route);
      return "redirect:/climbs/route";
   }

   @RequestMapping("/{id}/edit")
   public String editRoute(@PathVariable String id, Model model) {
      Route route = routeService.findClimbById(Long.parseLong(id));

      if(route == null) {
         throw new EntityNotFoundException("Route", id);
      }

      model.addAttribute("route", route);
      model.addAttribute("grades", gradeService.getGrades());
      return "create/create_route";
   }
}

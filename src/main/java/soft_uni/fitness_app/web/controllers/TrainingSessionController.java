package soft_uni.fitness_app.web.controllers;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.fitness_app.trainingSession.model.TrainingSession;
import soft_uni.fitness_app.trainingSession.service.TrainingSessionService;

import java.util.Optional;
import java.util.UUID;

@Controller
public class TrainingSessionController {

 private final TrainingSessionService trainingSessionService;

    public TrainingSessionController(TrainingSessionService trainingSessionService) {

        this.trainingSessionService = trainingSessionService;
    }

    @GetMapping("{id}/details")
    public ModelAndView showTrainingSessionDetails( @PathVariable("id") UUID id) {
        Optional<TrainingSession> trainingSessionOpt= this.trainingSessionService.findById(id);
        if (trainingSessionOpt.isEmpty()) {
            throw new RuntimeException("No training session found with id: \"" + id + "\"");
        }
        ModelAndView modelAndView = new ModelAndView("session-details");
        modelAndView.addObject("trainingSession", trainingSessionOpt.get());
        return modelAndView;
    }
}

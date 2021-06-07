package io.github.brzezik919.controller;

import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.service.OpinionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OpinionController {

    private final OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @RequestMapping(value="/yourProfile/offer/{id}/gradeTransaction", method = RequestMethod.POST, params ="gradeTransaction=true")
    public String acceptUser(@PathVariable int id, @ModelAttribute Transaction transaction, Authentication auth) {
        opinionService.setGrade(id, true, auth.getName());
        return "redirect:/yourProfile/offer/{id}";
    }

    @RequestMapping(value="/yourProfile/offer/{id}/gradeTransaction", method = RequestMethod.POST, params ="gradeTransaction=false")
    public String refuseUser(@PathVariable int id, @ModelAttribute Transaction transaction, Authentication auth) {
        opinionService.setGrade(id, false, auth.getName());
        return "redirect:/yourProfile/offer/{id}";
    }
}

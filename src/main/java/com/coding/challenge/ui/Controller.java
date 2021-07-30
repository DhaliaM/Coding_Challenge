package com.coding.challenge.ui;



import com.coding.challenge.service.ChallengeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class Controller {
    final ChallengeService challengeService;

    public Controller(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public void startChallenge(@RequestBody String request) throws JsonProcessingException {
        ChallengeDto challengeDto = new ObjectMapper().readValue(request,ChallengeDto.class);

        challengeService.selectedChallenge(challengeDto.getChallengeId());
    }

}

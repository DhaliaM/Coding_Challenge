package com.coding.challenge.ui;

import com.coding.challenge.service.Challenge;
import com.coding.challenge.service.ChallengeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    final ChallengeService challengeService;
    List<Challenge> challengeList;

    public Controller(ChallengeService challengeService, List<Challenge> challengeList) {
        this.challengeList = challengeList;
        this.challengeService = challengeService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("challengeList", challengeList);

        return "index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public @ResponseBody
    ChallengeDto startChallenge(@RequestBody String request) throws JsonProcessingException {
        ChallengeDto challengeDto = new ObjectMapper().readValue(request, ChallengeDto.class);
        challengeDto = challengeService.selectedChallenge(challengeDto.getChallengeId());

        return challengeDto;
    }

}

package com.coding.challenge.ui;

import com.coding.challenge.service.Challenge;
import com.coding.challenge.service.ChallengeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ein RestController für den Seitenaufbau.
 * Die Kommunikation erfolgt über JSON.
 *
 * @author Dhalia
 */
@Controller
public class UiController {
    final ChallengeService challengeService;
    final List<Challenge> challengeList;

    public UiController(ChallengeService challengeService, List<Challenge> challengeList) {
        this.challengeList = challengeList;
        this.challengeService = challengeService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("challengeList", challengeList);

        return "index";
    }

    @PostMapping(value = {"/", "/index"})
    @ResponseBody
    public ChallengeDto startChallenge(@RequestBody ChallengeDto challengeDto) {
        challengeDto = challengeService.selectedChallenge(challengeDto.getChallengeId());

        return challengeDto;
    }

}

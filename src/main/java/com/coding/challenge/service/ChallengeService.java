package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ein Service um die unterschiedlichen Challenges zu starten.
 *
 * @author Dhalia
 */
@Service
public class ChallengeService {
    private final List<Challenge> challenges;

    public ChallengeService(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    /**
     * Startet die ausgewählte Challenge.
     *
     * @param id - Id der zu startenden Challenge
     * @return Objekt vom Typ ChallengeDto
     */
    public ChallengeDto selectedChallenge(int id) {
        ChallengeDto challengeDto = null;
        for (Challenge challenge : challenges) {
            if (id == challenge.getId()) {
                challengeDto = challenge.runChallenge();
            }
        }

        return challengeDto;
    }

}

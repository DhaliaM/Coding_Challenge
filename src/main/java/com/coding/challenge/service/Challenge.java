package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;

/**
 * Interface für eine Challenge
 *
 * @author Dhalia
 */
public interface Challenge {
    int getId();

    ChallengeDto runChallenge();
}

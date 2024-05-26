package cz.asen.unicorn.fridge.domain.enums;

public enum ClaimState {
    UNCLAIMED, // Created
    WAITING,   // Waiting for donor to accept
    ACCEPTED,  // Waiting for claimee to arrive
    CLAIMED    // Successfully claimed and taken
}

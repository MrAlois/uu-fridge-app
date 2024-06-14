package cz.asen.unicorn.fridge.domain.enums;

public enum ClaimState {
    UNCLAIMED, // Created
    CLAIMED,   // Waiting for donor to accept
    FINISHED   // Successfully claimed and taken
}

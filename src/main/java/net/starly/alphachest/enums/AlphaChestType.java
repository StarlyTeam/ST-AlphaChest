package net.starly.alphachest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum AlphaChestType {
    
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5);

    @Getter
    private final int value;


    public static AlphaChestType valueOf(int value) {
        Optional<AlphaChestType> result = Arrays.stream(values()).filter(v -> v.getValue() == value).findFirst();
        return result.orElse(null);
    }
}

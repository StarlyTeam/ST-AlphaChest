package net.starly.alphachest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MessageType {
    NORMAL("messages.alphachest"), ERROR("errorMessages.alphachest");

    @Getter
    private final String path;
}

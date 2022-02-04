package com.blck_rbbit.felix.bot;

import com.blck_rbbit.felix.handlers.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (!isFillingProfileState(currentState)) {
            return messageHandlers.get(BotState.ASK_START);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case ASK_START:
            case ASK_ABOUT:
            case ASK_HELP:
            case ASK_CONTACTS:
            case ASK_CANCEL:
            case ASK_CANCEL_AUTH:
            case ASK_AUTH:
            case UNDEFINED:
                return true;
            default:
                return false;
        }
    }
}

package com.blck_rbbit.felix.handlers.state_handlers;

import com.blck_rbbit.felix.bot.BotState;
import com.blck_rbbit.felix.cache.UserDataCache;
import com.blck_rbbit.felix.handlers.InputMessageHandler;
import com.blck_rbbit.felix.services.KeyboardService;
import com.blck_rbbit.felix.services.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class AskAuthHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private KeyboardService keyboardService;

    public AskAuthHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_AUTH;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        keyboardService.init();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId,"reply.askAuth");
        replyToUser.setReplyMarkup(keyboardService.getAuthKeyboard());
        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AUTH);
        return replyToUser;
    }

    @Autowired
    public void setKeyboardService(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }
}

package com.blck_rbbit.felix.handlers.state_handlers;

import com.blck_rbbit.felix.bot.BotState;
import com.blck_rbbit.felix.cache.UserDataCache;
import com.blck_rbbit.felix.handlers.InputMessageHandler;
import com.blck_rbbit.felix.services.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class AskAboutHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public AskAboutHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_ABOUT;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId,"reply.askAbout");
        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_ABOUT);
        return replyToUser;
    }
}

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
public class AskHelpHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public AskHelpHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_HELP;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId,"reply.askHelp");
        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_HELP);
        return replyToUser;
    }
}

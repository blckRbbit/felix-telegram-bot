package com.blck_rbbit.felix.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ReplyMessagesService {

    private final LocaleMessageService localeMessageService;

    public ReplyMessagesService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, args));
    }
}

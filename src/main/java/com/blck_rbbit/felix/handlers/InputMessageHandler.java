package com.blck_rbbit.felix.handlers;

import com.blck_rbbit.felix.bot.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {
    SendMessage handle(Message message);
    BotState getHandlerName();
}

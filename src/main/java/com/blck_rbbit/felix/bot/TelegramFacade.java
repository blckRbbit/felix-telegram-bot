package com.blck_rbbit.felix.bot;

import com.blck_rbbit.felix.cache.UserDataCache;
import com.blck_rbbit.felix.services.*;
import com.blck_rbbit.felix.utils.Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.blck_rbbit.felix.utils.Emojis;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;
    private final ReplyMessagesService replyMessagesService;
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final Util util;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache,
                          ReplyMessagesService replyMessagesService, UserService userService, KeyboardService keyboardService, Util util) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.replyMessagesService = replyMessagesService;
        this.userService = userService;
        this.keyboardService = keyboardService;
        this.util = util;
    }

    public BotApiMethod<?> handleUpdate(Update update){
        SendMessage replyMessage = null;
        Long telegramUserId;
        String userName;
        String link;
        
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            telegramUserId = Long.valueOf(callbackQuery.getFrom().getId());
            link = String.format("%sapi/v1/idea/%s", util.getUrl(), telegramUserId);
            keyboardService.sendIdeaBtn.setUrl(link);
            userName = update.getCallbackQuery().getFrom().getUserName();
            log.info("New callbackQuery from User:{}, chatId: {},  with text: {}",
                    userName, telegramUserId, update.getCallbackQuery().getData());
            userService.saveUserToDataBase(telegramUserId, userName);
            
            return processCallbackQuery(callbackQuery);
        }
        
        if (message != null && message.hasText()) {
            userName = message.getFrom().getUserName();
            telegramUserId = Long.valueOf(message.getFrom().getId());
            link = String.format("%sapi/v1/idea/%s", util.getUrl(), telegramUserId);
            keyboardService.sendIdeaBtn.setUrl(link);
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    userName, telegramUserId, message.getText());
            userService.saveUserToDataBase(telegramUserId, userName);
            replyMessage = handleInputMessage(message);
        }
        
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.ASK_START;
                break;
            case "/admin":
                botState = BotState.ASK_AUTH;
                break;
            default:
                botState = BotState.UNDEFINED;
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

    @SneakyThrows
    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = sendAnswerCallBackQuery(
                Emojis.POINT_RIGHT + "Воспользуйтесь меню", false, buttonQuery);
        if (buttonQuery.getData().equals("/about")) {
            SendMessage replyToUser = replyMessagesService.getReplyMessage(chatId,"reply.askAbout");
            callBackAnswer = sendAnswerCallBackQuery(replyToUser.getText(), true, buttonQuery);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_ABOUT);
        } else if (buttonQuery.getData().equals("/cancel")) {
            SendMessage replyToUser = replyMessagesService.getReplyMessage(chatId,"reply.askCancel");
            callBackAnswer = sendAnswerCallBackQuery(replyToUser.getText(), false, buttonQuery);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CANCEL);
        } else if (buttonQuery.getData().equals("/cancelauth")) {
            SendMessage replyToUser = replyMessagesService.getReplyMessage(chatId,"reply.askCancelAuth");
            callBackAnswer = sendAnswerCallBackQuery(replyToUser.getText(), false, buttonQuery);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CANCEL_AUTH);
        } else if (buttonQuery.getData().equals("/help")) {
            SendMessage replyToUser = replyMessagesService.getReplyMessage(chatId,"reply.askHelp");
            callBackAnswer = sendAnswerCallBackQuery(replyToUser.getText(), true, buttonQuery);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_HELP);
        } else if (buttonQuery.getData().equals("/contacts")) {
            SendMessage replyToUser = replyMessagesService.getReplyMessage(chatId,"reply.askContacts");
            callBackAnswer = sendAnswerCallBackQuery(replyToUser.getText(), true, buttonQuery);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CONTACTS);
        }
        return callBackAnswer;
    }
    
    private AnswerCallbackQuery sendAnswerCallBackQuery (String text, boolean alert, CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }
    
    @SneakyThrows
    private AnswerCallbackQuery sendAnswerCallBackQuery(String link, CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        
        return answerCallbackQuery;
    }
}

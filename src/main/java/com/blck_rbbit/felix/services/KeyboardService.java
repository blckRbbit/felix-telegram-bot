package com.blck_rbbit.felix.services;

import com.blck_rbbit.felix.utils.Util;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class KeyboardService {
    @Autowired
    private final Util util = new Util();
    private final String url = util.getUrl() + "auth";
    private final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardMarkup authKeyboard = new InlineKeyboardMarkup();
    private final InlineKeyboardButton aboutBtn = new InlineKeyboardButton().setText("Обо мне");
    private final InlineKeyboardButton helpBtn = new InlineKeyboardButton().setText("Помощь");
    private final InlineKeyboardButton contactBtn = new InlineKeyboardButton().setText("Контакты");
    private final InlineKeyboardButton cancelBtn = new InlineKeyboardButton().setText("Отмена");
    public InlineKeyboardButton sendIdeaBtn = new InlineKeyboardButton().setText("Есть идея!");
    private final InlineKeyboardButton yesBtn = new InlineKeyboardButton().setText("Да")
            .setUrl(url);
    private final InlineKeyboardButton noBtn = new InlineKeyboardButton().setText("Нет");

    @PostConstruct
    public InlineKeyboardMarkup init() {
        aboutBtn.setCallbackData("/about");
        helpBtn.setCallbackData("/help");
        contactBtn.setCallbackData("/contacts");
        cancelBtn.setCallbackData("/cancel");
        yesBtn.setCallbackData("/auth");
        noBtn.setCallbackData("/cancelauth");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(aboutBtn);
        keyboardButtonsRow1.add(helpBtn);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(contactBtn);
        keyboardButtonsRow2.add(cancelBtn);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(sendIdeaBtn);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getAuthKeyboard() {
        List<InlineKeyboardButton> authButtonsRow = new ArrayList<>();
        authButtonsRow.add(yesBtn);
        authButtonsRow.add(noBtn);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(authButtonsRow);
        authKeyboard.setKeyboard(rowList);
        return authKeyboard;
    }

}

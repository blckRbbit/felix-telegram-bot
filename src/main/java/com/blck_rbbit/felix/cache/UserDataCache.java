package com.blck_rbbit.felix.cache;

import com.blck_rbbit.felix.bot.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final Map<Integer, BotState> usersBotStates = new HashMap<>();
    
    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_START;
        }
        return botState;
    }
}

package com.blck_rbbit.felix.cache;

import com.blck_rbbit.felix.bot.BotState;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);
    BotState getUsersCurrentBotState(int userId);
}

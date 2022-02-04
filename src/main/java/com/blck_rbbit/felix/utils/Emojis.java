package com.blck_rbbit.felix.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    CAT(EmojiParser.parseToUnicode(":cat:")),
    PHONE(EmojiParser.parseToUnicode(":telephone_receiver:")),
    POINT_DOWN(EmojiParser.parseToUnicode(":point_down:")),
    POINT_RIGHT(EmojiParser.parseToUnicode(":point_right:"));


    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}

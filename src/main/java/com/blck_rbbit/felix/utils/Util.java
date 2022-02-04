package com.blck_rbbit.felix.utils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
@Slf4j
@RequiredArgsConstructor
public class Util {
    private final String urlToken = String.format(
            "https://api.telegram.org/bot%s/sendMessage", getToken()
    );
    @SneakyThrows
    public String getToken() {
        FileInputStream in;
        Properties property = new Properties();
        in = new FileInputStream("src/main/resources/application.properties");
        property.load(in);
        return property.getProperty("telegrambot.botToken");
    }
    
    public String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return passwordGenerator.generate(8);
    }
    
    @SneakyThrows
    public String getUrl() {
        FileInputStream in;
        Properties property = new Properties();
        in = new FileInputStream("src/main/resources/application.properties");
        property.load(in);
        return property.getProperty("telegrambot.webHookPath") + "/";
    }
    
    @SneakyThrows
    public void createContent(byte[] postData) {
        HttpURLConnection connection;
        StringBuilder content;
        URL url = new URL(urlToken);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Java upread.ru client");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(postData);
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        content = new StringBuilder();
        while ((line = br.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
    }
    
    public byte[] createPostData(Long chatId, String data) {
        return String.format("chat_id=%s&text=%s", chatId, data).getBytes(StandardCharsets.UTF_8);
    }
}

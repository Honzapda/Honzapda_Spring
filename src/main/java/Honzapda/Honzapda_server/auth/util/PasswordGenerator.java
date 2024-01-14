package Honzapda.Honzapda_server.auth.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+";

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();

        // 소문자, 대문자, 숫자, 특수 문자를 각각 하나 이상 포함하도록 랜덤 비밀번호 생성
        password.append(getRandomCharacter(LOWERCASE));
        password.append(getRandomCharacter(UPPERCASE));
        password.append(getRandomCharacter(DIGITS));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        // 나머지 문자를 랜덤하게 추가
        for (int i = 4; i < length; i++) {
            String characterSet = getRandomCharacterSet();
            password.append(getRandomCharacter(characterSet));
        }

        // 랜덤 문자열을 섞어서 반환
        return shuffleString(password.toString());
    }

    private static char getRandomCharacter(String characterSet) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    private static String getRandomCharacterSet() {
        String[] characterSets = { LOWERCASE, UPPERCASE, DIGITS, SPECIAL_CHARACTERS };
        int randomIndex = random.nextInt(characterSets.length);
        return characterSets[randomIndex];
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        String randomPassword = generateRandomPassword(12); // 12글자 랜덤 비밀번호 생성
        System.out.println("Random Password: " + randomPassword);
    }
}

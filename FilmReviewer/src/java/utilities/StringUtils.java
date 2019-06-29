/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class StringUtils implements Serializable{
    public static String formatString(String str) {
//        return str.replaceAll("[áàạảãâấầậẩẫăắằặẳẵ]", "a")
//                .replaceAll("[óòọỏõôốồộổỗơớờởỡợ]", "o")
//                .replaceAll("[íìịỉĩ]", "i")
//                .replaceAll("[éèẹẻẽêếềệểễ]", "e")
//                .replaceAll("[úùụủũưứừựửữ]", "u")
//                .replaceAll("[đ]", "d");
        return Normalizer.normalize(str, Normalizer.Form.NFC);

    }

    public static int[] getNumberOfWordContain(String sample, String value) {
        String[] arr = value.toLowerCase().trim().split(" ");

        int count = 0;
        int index = -1;

        if (sample.contains(arr[0])) {
            for (int i = 0; i < arr.length; i++) {
                if (sample.contains(arr[i])) {
                    count++;
                    index = i;
                }
            }
        }

        return new int[]{count, index};
    }

    public static boolean isExistInList(List<String> list, String value) {

        for (String item : list) {
            if (value.trim().toLowerCase().equals(item.trim().toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static String replaceHTMLCodeCharacter(String value) {

        return value.replaceAll("&#224;", "à")
                .replaceAll("&#242;", "ò")
                .replaceAll("&#243;", "ó")
                .replaceAll("&#234;", "ê")
                .replaceAll("&#234;", "ê")
                .replaceAll("&#227;", "ã")
                .replaceAll("&#226;", "â")
                .replaceAll("&#233;", "é")
                .replaceAll("&#244;", "ô")
                .replaceAll("&#225;", "á")
                .replaceAll("&#237;", "í")
                .replaceAll("&#250;", "ú")
                .replaceAll("&#249;", "ù")
                .replaceAll("&#236;", "ì")
                .replaceAll("&#253;", "ý")
                .replaceAll("&#232;", "è")
                .replaceAll("&#204;", "Ì")
                .replaceAll("&#193;", "Á")
                .replaceAll("&#192;", "À");

    }
}

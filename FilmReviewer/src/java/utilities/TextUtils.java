/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xmlchecker.XMLSyntaxChecker;

/**
 *
 * @author ASUS
 */

public class TextUtils {
    public static String refineHTML(String src){
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        
        XMLSyntaxChecker xMLSyntaxChecker = new XMLSyntaxChecker();
        src = xMLSyntaxChecker.check(src);
        
        return src;
    }
    
    public static String getBody(String src){
        String result = src;
        
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);
        
        Matcher matcher = pattern.matcher(result);
        
        if (matcher.find()){
            result = matcher.group();
        }
        
        return result;
    }
    
    public static String removeMiscellaneousTags(String src){
        String result = src;
        
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        expression  = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        return result;
    }
    
    public static String getString(InputStream inputStream) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(StringUtils.replaceHTMLCodeCharacter(line));
            }
            return stringBuilder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}

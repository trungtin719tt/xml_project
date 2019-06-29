/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import static xmlchecker.SyntaxState.*;

/**
 *
 * @author ASUS
 */
public class XMLSyntaxChecker {
    public XMLSyntaxChecker() {
    }

    public String check(String src) {
        src = src + " ";
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();

        StringBuilder openTag = new StringBuilder();
        boolean isEmptyTag = false, isOpenTag = false, isCloseTag = false;
        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        Map<String, String> attributes = new HashMap<String, String>();

        StringBuilder content = new StringBuilder();

        Stack<String> stack = new Stack<>();

        String state = CONTENT;

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            if (state.equals(CONTENT)) {
                if (c == LT) {
                    state = OPEN_BRACKET;
                    writer.append(content.toString().trim().replace("&", "&amp;"));
                } else {
                    content.append(c);
                }
            } else if (state.equals(OPEN_BRACKET)) {
                if (isStartTagChars(c)) {
                    state = OPEN_TAG_NAME;

                    isOpenTag = true;
                    isCloseTag = false;
                    isEmptyTag = false;

                    openTag.setLength(0);
                    openTag.append(c);
                } else if (c == SLASH) {
                    state = CLOSE_TAG_SLASH;

                    isOpenTag = false;
                    isCloseTag = true;
                    isEmptyTag = false;
                } else if (c == QUESTION_MARK) {
                    state = PROCESS_INSTRUCTION;
                }
            } else if (state.equals(PROCESS_INSTRUCTION)) {
                if (c == GT) {
                    state = CONTENT;
                }
            } else if (state.equals(OPEN_TAG_NAME)) {
                if (isTagChars(c)) {
                    openTag.append(c);
                } else if (isSpace(c)) {
                    state = TAG_INNER;

                    attributes.clear();
                } else if (c == GT) {
                    state = CLOSE_BRACKET;
                } else if (c == SLASH) {
                    state = EMPTY_SLASH;
                }
            } else if (state.equals(TAG_INNER)) {
                if (isSpace(c)) {
                    //loop
                } else if (isStartAttrChars(c)) {
                    state = ATTR_NAME;

                    attrName.setLength(0);
                    attrName.append(c);
                } else if (c == GT) {
                    state = CLOSE_BRACKET;
                } else if (c == SLASH) {
                    state = EMPTY_SLASH;
                }
            } else if (state.equals(ATTR_NAME)) {
                if (isAttrChars(c)) {
                    attrName.append(c);
                } else if (c == EQ) {
                    state = EQUAL;
                } else if (isSpace(c)) {
                    state = EQUAL_WAIT;
                } else {
                    if (c == SLASH) {
                        attributes.put(attrName.toString(), "true");
                        state = EMPTY_SLASH;
                    } else if (c == GT) {
                        attributes.put(attrName.toString(), "true");
                        state = CLOSE_BRACKET;
                    }
                }
            } else if (state.equals(EQUAL_WAIT)) {
                if (isSpace(c)) {

                } else if (c == EQ) {
                    state = EQUAL;
                } else {
                    if (isStartAttrChars(c)) {
                        attributes.put(attrName.toString(), "true");
                        state = ATTR_NAME;

                        attrName.setLength(0);
                        attrName.append(c);
                    }
                }
            } else if (state.equals(EQUAL)) {
                if (isSpace(c)) {

                } else if (c == D_QOUT || c == S_QOUT) {
                    qoute = c;
                    state = ATTR_VALUE_Q;
                    attrValue.setLength(0);
                } else if (!isSpace(c) && c != GT) {
                    state = ATTR_VALUE_NQ;

                    attrValue.setLength(0);
                    attrValue.append(c);
                }
            } else if (state.equals(ATTR_VALUE_Q)) {
                if (c != qoute) {
                    attrValue.append(c);
                } else if (c == qoute) {
                    state = TAG_INNER;
                    attributes.put(attrName.toString(), attrValue.toString());
                }
            } else if (state.equals(ATTR_VALUE_NQ)) {
                if (!isSpace(c) && c != GT) {
                    attrValue.append(c);
                } else if (isSpace(c)) {
                    state = TAG_INNER;
                    attributes.put(attrName.toString(), attrValue.toString());
                } else if (c == GT) {
                    state = CLOSE_BRACKET;
                    attributes.put(attrName.toString(), attrValue.toString());
                }
            } else if (state.equals(EMPTY_SLASH)) {
                if (c == GT) {
                    state = CLOSE_BRACKET;
                    isEmptyTag = true;
                }
            } else if (state.equals(CLOSE_TAG_SLASH)) {
                if (isStartTagChars(c)) {
                    state = CLOSE_TAG_NAME;

                    closeTag.setLength(0);
                    closeTag.append(c);
                }

            } else if (state.equals(CLOSE_TAG_NAME)) {
                if (isTagChars(c)) {
                    closeTag.append(c);
                } else if (isSpace(c)) {
                    state = WAIT_END_TAG_CLOSE;
                } else if (c == GT) {
                    state = CLOSE_BRACKET;
                }

            } else if (state.equals(WAIT_END_TAG_CLOSE)) {
                if (isSpace(c)) {

                } else if (c == GT) {
                    state = CLOSE_BRACKET;
                }

            } else if (state.equals(CLOSE_BRACKET)) {
                if (isOpenTag) {
                    String openTagName = openTag.toString().toLowerCase();

                    if (INLINE_TAGS.contains(openTagName)) {
                        isEmptyTag = true;
                    }
                    writer.append(LT)
                            .append(openTagName)
                            .append(convert(attributes))
                            .append(isEmptyTag ? "/" : "")
                            .append(GT);

                    attributes.clear();

                    if (!isEmptyTag) {
                        stack.push(openTagName);
                    }
                } else if (isCloseTag) {
                    String closeTagName = closeTag.toString().toLowerCase();

                    if (!stack.isEmpty() && stack.contains(closeTagName)) {
                        while (!stack.isEmpty() && !stack.peek().equals(closeTagName)) {
                            writer.append(LT)
                                    .append(SLASH)
                                    .append(stack.pop())
                                    .append(GT);
                        }
                        if (!stack.isEmpty() && stack.peek().equals(closeTagName)) {
                            writer.append(LT)
                                    .append(SLASH)
                                    .append(stack.pop())
                                    .append(GT);
                        }
                    }
                }

                if (c == LT) {
                    state = OPEN_BRACKET;
                } else {
                    state = CONTENT;

                    content.setLength(0);
                    content.append(c);
                }
            }

        }

        if (state.equals(CONTENT)) {
            writer.append(content.toString().trim().replace("&", "&amp;"));
        }

        while (!stack.isEmpty()) {
            writer.append(LT)
                    .append(SLASH)
                    .append(stack.pop())
                    .append(GT);
        }

        return writer.toString();
    }

    private Character qoute;

    public static String convert(Map<String, String> attributes) {
        if (attributes.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replaceAll("\"", "&qout;")
                    .replaceAll("'", "&apos;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;");

            builder.append(entry.getKey())
                    .append("=")
                    .append("\"")
                    .append(value)
                    .append("\"")
                    .append(" ");

        }

        String result = builder.toString().trim();
        if (!result.equals("")) {
            result = " " + result;
        }

        return result;
    }
}

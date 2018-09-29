package com.netty.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wangjian on 2018/9/29.
 */
public class InputUtil {

    private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));

    private InputUtil(){}

    /**
     * 获取一个键盘输入的字符串数据，如果数据为空则重新输入
     * @param prompt 提示内容
     * @return 输入内容
     */
    public static String getString(String prompt) {
        String content = null;
        boolean bFlag = true;

        while (bFlag) {
            System.out.println(prompt);

            try {
                content = KEYBOARD_INPUT.readLine();
                if (content == null || "".equals(content)) {
                    System.out.println("输入的内容不能为空，请重新输入！");
                } else {
                    bFlag = false;
                }
            } catch (IOException e) {
                System.out.println("输入数据错误，请重新输入");
                e.printStackTrace();
            }
        }
        return content;
    }
}

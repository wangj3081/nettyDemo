package com.netty.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.netty.demo.vo.Member;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * msgpack的使用
 * Created by wangjian on 2019/1/27.
 */
public class MsgpackTest {

    public static void main(String[] args) throws IOException {
//        packList();
        packMap();

    }

    private static void packMap() throws IOException {
        Map<String, Member> packMap = new HashMap<String, Member>();
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            int number = random.nextInt(Integer.MAX_VALUE);
            Member member = new Member();
            member.setName("小米" + i);
            member.setAge(number % i);
            member.setSalary(number % i * 1000.5);
            packMap.put("key" + i, member);
        }
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(packMap);

        Map<String, Member> read = messagePack.read(write, Templates.tMap(messagePack.lookup(String.class), messagePack.lookup(Member.class)));
        System.err.println(JSONObject.toJSONString(read));
    }

    private static void packList() {
        List<Member> list = new ArrayList<Member>();
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            int number = random.nextInt(Integer.MAX_VALUE);
            Member member = new Member();
            member.setName("小米" + i);
            member.setAge(number % i);
            member.setSalary(number % i * 1000.5);
            list.add(member);
        }

        MessagePack messagePack = new MessagePack();
        try {
            byte[] write = messagePack.write(list); // 写入数据
            // 通过二进制数据读取，转换出所需要的内容
            List<Member> read = messagePack.read(write, Templates.tList(messagePack.lookup(Member.class)));
            System.err.println(JSONObject.toJSONString(read));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

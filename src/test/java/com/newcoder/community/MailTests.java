package com.newcoder.community;
import com.newcoder.community.dao.AlphaDao;
import com.newcoder.community.service.AlphaService;
import com.newcoder.community.util.MailClient;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("mjjshu@163.com", "test", "welcome.");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "majiajun");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("mjjshu@163.com", "test", content);


    }

}

package controller;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * 测试.
 *
 * @author zhao.xudong
 */
@Controller
public class SimpleController {

    @Resource
    private DataSource dataSource;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        System.out.println(dataSource.getCreatedCount());
        return "hello";
    }

}

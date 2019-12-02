package com.yanzheng.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * configure spring and junit, load springIOC container when junit
 * runs
 * 
 * @author yanzheng
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// tell junit the location of spring cong file
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {

}

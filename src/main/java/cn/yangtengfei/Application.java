package cn.yangtengfei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

@SpringBootApplication
@ComponentScan("cn.yangtengfei")
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Application.class);
        //app.setWebEnvironment(false);
        app.run(args);
    }
}

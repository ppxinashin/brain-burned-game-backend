package com.jeholppx.bbg;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 19:00
 */
@SpringBootTest
class MainApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void schedulerTest() {
        Scheduler io = Schedulers.io();
        while (true) {
            io.scheduleDirect(() -> {
                System.out.println(Thread.currentThread().getName() + " print hello");
                try {
                    Thread.sleep(50000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}

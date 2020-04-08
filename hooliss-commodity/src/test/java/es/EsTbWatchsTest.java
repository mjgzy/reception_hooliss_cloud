package es;


import com.xfkj.Commodity_Provider_APP;
import com.xfkj.mapper.commodity.EsTbWatchsRepository;
import com.xfkj.pojo.commodity.TbWatchs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Commodity_Provider_APP.class)
public class EsTbWatchsTest {
    @Autowired
    private EsTbWatchsRepository esTbWatchsRepository;
    @Test
    public void Test1(){
        Optional<TbWatchs> byId = esTbWatchsRepository.findById(98);
        System.err.println(byId.get());;
    }
}

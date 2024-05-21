package backend.dbms.PocketBrawlers;

import backend.dbms.PocketBrawlers.CPU.CPU;
import backend.dbms.PocketBrawlers.CPU.CPURepository;
import backend.dbms.PocketBrawlers.Services.CPUService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCPUController {
//    @Mock
//    private CPUService cs;
//
//    @Autowired
//    private CPURepository repo;  // this is the mock one
//
//    @Before
//    public void config(){
//        CPU c = new CPU("url", 1);
//        repo.save(c);
//    }
//    @Test
//    public void testCPU()  {
//        // Set up MOCK methods for the REPO
//
//
//        List<CPU> l = repo.findAll();
//
//        CPU tester = l.get(156);
//
//
//
////
////        // mock the findAll method return the fixed list
////        when(repo.findAll()).thenReturn(l);
////
////        // mock the save() method to save argument to the list
////        when(repo.save((CPU)any(CPU.class)))
////                .thenAnswer(x -> {
////                    CPU r = x.getArgument(0);
////                    l.add(r);
////                    return null;
////                });
//
//        // what does this print
//        //System.out.println();
//        assertEquals(tester.getLevel(), 2);
//    }

}

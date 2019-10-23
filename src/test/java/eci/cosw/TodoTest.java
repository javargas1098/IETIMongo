package eci.cosw;

import eci.cosw.data.TodoRepository;
import eci.cosw.data.model.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void findByResponsibleTest(){
        todoRepository.deleteAll();

        Todo todo1 = new Todo("travel1", 11, "Jan 11 - 1690", "charles@example.com", "pending1");
        Todo todo2 = new Todo(" IETI",10,"Jan 10 - 2019","test@test.com","done");
        Todo todo3 = new Todo("travel1", 33, "Jan 11 - 1690", "charles@example.com", "pending1");

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> answer = todoRepository.findByResponsible("charles@example.com");
        for( Todo todo: answer){
            Assert.assertEquals(2, answer.size());
            Assert.assertTrue(answer.contains(todo1));
            Assert.assertTrue(answer.contains(todo3));
        }
    }

}


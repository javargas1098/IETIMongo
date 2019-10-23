package eci.cosw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;

import eci.cosw.config.AppConfiguration;
import eci.cosw.data.CustomerRepository;
import eci.cosw.data.TodoRepository;
import eci.cosw.data.UserRepository;
import eci.cosw.data.model.Customer;
import eci.cosw.data.model.Todo;
import eci.cosw.data.model.User;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TodoRepository todoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
		MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");
		customerRepository.deleteAll();

		customerRepository.save(new Customer("Alice", "Smith"));
		customerRepository.save(new Customer("Bob", "Marley"));
		customerRepository.save(new Customer("Jimmy", "Page"));
		customerRepository.save(new Customer("Freddy", "Mercury"));
		customerRepository.save(new Customer("Michael", "Jackson"));

		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customerRepository.findAll()) {
			System.out.println(customer);
		}
		System.out.println("-------------------------------");
		userRepository.deleteAll();

		userRepository.save(new User("Charles1", "example@example.com"));
		userRepository.save(new User("Charles2", "example@example.com"));
		userRepository.save(new User("Charles3", "example@example.com"));
		userRepository.save(new User("Charles4", "example@example.com"));
		userRepository.save(new User("Charles5", "example@example.com"));
		userRepository.save(new User("Charles6", "example@example.com"));
		userRepository.save(new User("Charles7", "example@example.com"));
		userRepository.save(new User("Charles8", "example@example.com"));
		userRepository.save(new User("Charles9", "charles@example.com"));
		userRepository.save(new User("Charles10", "charles@example.com"));

		System.out.println("-------------------------------");
		todoRepository.deleteAll();
		todoRepository.save(new Todo("travel1", 11, "Jan 11 - 1690", "chars@example1.com", "pending1"));
		todoRepository.save(new Todo("travel2", 12, "Jan 11 - 1690", "chars@example2.com", "pending1"));
		todoRepository.save(new Todo("travel3", 13, "Jan 11 - 1690", "chars@example3.com", "pending1"));
		todoRepository.save(new Todo("travel4", 14, "Jan 11 - 1690", "chars@example4.com", "pending1"));
		todoRepository.save(new Todo("travel5", 15, "Jan 11 - 1690", "chars@example5.com", "pending1"));
		todoRepository.save(new Todo("travel6", 16, "Jan 11 - 1690", "chars@example5.com", "pending1"));
		todoRepository.save(new Todo("travel7", 17, "Jan 11 - 1690", "chars@example5.com", "pending1"));
		todoRepository.save(new Todo("travel8", 18, "Jan 11 - 1690", "chars@example5.com", "pending1"));
		todoRepository.save(new Todo("travel9", 19, "Jan 11 - 1690", "chars@example6.com", "pending1"));
		todoRepository.save(new Todo("travel10", 21, "Jan 11 - 1690", "chars@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel11", 22, "Jan 11 - 1690", "charles@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel12", 23, "Jan 11 - 1690", "chars@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel13", 24, "Jan 11 - 1690", "charles@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel14", 25, "Jan 11 - 1690", "charles@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel15", 26, "Jan 11 - 1690", "charles@exampl6e.com", "pending1"));
		todoRepository.save(new Todo("travel16", 27, "Jan 11 - 1690", "chars@exampl7e.com", "pending1"));
		todoRepository.save(new Todo("travel17", 28, "Jan 11 - 1690", "charles@exampl7e.com", "pending1"));
		todoRepository.save(new Todo("travel18", 29, "Jan 11 - 1690", "charles@exampl7e.com", "pending1"));
		todoRepository.save(new Todo("travel19", 30, "Jan 11 - 1690", "chars@exampl7e.com", "pending1"));
		todoRepository.save(new Todo("travel20", 32, "Jan 11 - 1690", "charles@exampl8e.com", "pending1"));
		todoRepository.save(new Todo("travel21", 33, "Jan 11 - 1690", "chars@exampl8e.com", "pending1"));
		todoRepository.save(new Todo("travel22", 34, "Jan 11 - 1690", "chars@exampl9e.com", "pending1"));
		todoRepository.save(new Todo("travel23", 35, "Jan 11 - 1690", "charles@exampl9e.com", "pending1"));
		todoRepository.save(new Todo("travel24", 36, "Jan 11 - 1690", "chars@example.com", "pending1"));
		todoRepository.save(
				new Todo("final-------------------------", 37, "Jan 11 - 1690", "charles@example.com", "pending1"));

		System.out.println("Todos found with findByResponsible():");
		for (Todo todo : todoRepository.findByResponsible("charles@example.com")) {
			System.out.println(todo);
		}

		System.out.println("Todos that the dueDate has expire:");

		Query query = new Query();
		query.addCriteria(Criteria.where("dueDate").lt(new SimpleDateFormat("MMM dd - yyyy").format(new Date())));
		List<Todo> todos = mongoOperation.find(query, Todo.class);
		for (Todo todo : todos) {
			System.out.println(todo);
		}

		System.out.println("Todos that are assigned to given user and have priority greater equal to:");
		query = new Query();
		query.addCriteria(Criteria.where("responsible").is("charles@example1.com").and("priority").gte(5));
		todos = mongoOperation.find(query, Todo.class);
		for (Todo todo : todos) {
			System.out.println(todo);
		}

		System.out.println("List users that have assigned more than 2 Todos:");
		query = new Query();
		
		System.out.println("Todo list that contains the description with a length greater than 30 characters:");
		query = new Query();
		query.addCriteria(Criteria.where("description").regex(".{31,}"));
		todos = mongoOperation.find(query, Todo.class);
		for (Todo todo : todos) {
			System.out.println(todo);
		}

	}

}
package springbootfinal.indieWearhaul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("springbootfinal.indieWearhaul")
@SpringBootApplication
public class App {
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}

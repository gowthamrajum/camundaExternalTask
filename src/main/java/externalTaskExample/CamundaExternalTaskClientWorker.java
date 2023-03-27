package externalTaskExample;

import java.util.Map;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;


@SpringBootApplication
public class CamundaExternalTaskClientWorker implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CamundaExternalTaskClientWorker.class, args);
    }

    @Override
    public void run(String... args) {
        // Replace these values with your actual username and password
        String username = "srvOnlineOfferingAcc";
        String password = "b8H19V#OY*qzvI53bMpdv*Un";


        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://tccdfw.com/engine-rest")
                .asyncResponseTimeout(10000)
                .addInterceptor((ClientRequestInterceptor) new BasicAuthInterceptor(username, password))
                .build();

        
        client.subscribe("sampleTask")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    System.out.println("Processing external task: " + externalTask.getId());

                    Map variables = externalTask.getAllVariables();

                    String firstName = (String) variables.get("firstName");
                    String lastName = (String) variables.get("lastName");
                    
                    System.out.println(firstName+" "+lastName);

                    variables.put("firstName", firstName.toUpperCase());
                    variables.put("lastName", lastName.toUpperCase());


                    
                    
                    
                    
                    
                    // Complete the external task
                    externalTaskService.complete(externalTask);
                })
                .open();
   
    }

}


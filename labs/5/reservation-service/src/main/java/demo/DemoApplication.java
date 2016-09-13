package demo;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {


    @Bean
    HealthIndicator healthIndicator() {
        return () -> Health.status("I <3 Spring!").build();
    }


    @Bean
    GraphiteReporter graphiteReporter(MetricRegistry registry,
                                      @Value("${graphite.host}") String host,
                                      @Value("${graphite.port}") int port) {
        GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                .prefixedWith("reservations")
                .build(new Graphite(host, port));
        reporter.start(2, TimeUnit.SECONDS);
        return reporter;
    }
/*
    @Bean
    CommandLineRunner runner(ReservationRepository rr) {
        return args ->
                Arrays.asList("Marten,Josh,Dave,Mark,Mark,Juergen".split(","))
                        .forEach(x -> rr.save(new Reservation(x)));

    }
*/
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
/*
    @Component
    @RepositoryEventHandler
    public static class ReservationEventHandler {

        @Autowired
        private CounterService counterService;

        @HandleAfterCreate
        public void create(Reservation p) {
            count("reservations.create", p);
        }

        @HandleAfterSave
        public void save(Reservation p) {
            count("reservations.save", p);
            count("reservations." + p.getId() + ".save", p);
        }

        @HandleAfterDelete
        public void delete(Reservation p) {
            count("reservations.delete", p);
        }

        protected void count(String evt, Reservation p) {
//            LogstashMarker logstashMarker = Markers.append("event", evt)
//                    .and(Markers.append("reservationName", p.getReservationName()))
//                    .and(Markers.append("id", p.getId()));
//
//            LOGGER.info(logstashMarker, evt);

            this.counterService.increment(evt);
            this.counterService.increment("meter." + evt);
        }
    }*/
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}

@Component
class ReservationResourceProcessor implements ResourceProcessor<Resource<Reservation>> {

    @Override
    public Resource<Reservation> process(Resource<Reservation> reservationResource) {
        Reservation reservation = reservationResource.getContent();
        Long id = reservation.getId();
        String url = "http://aws.images.com/" + id + ".jpg";
        reservationResource.add(new Link(url, "profile-photo"));
        return reservationResource;
    }
}
/*
@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @RestResource(path = "by-name")
    Collection<Reservation> findByReservationName(@Param("rn") String rn);
}
*/

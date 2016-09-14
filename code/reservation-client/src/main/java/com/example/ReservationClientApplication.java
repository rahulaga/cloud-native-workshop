package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


//@EnableResourceServer
//@EnableBinding(ReservationChannels.class)
@EnableZuulProxy
//@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {

/*	@Bean
	//@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
*/
	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}

/*
interface ReservationChannels {

	@Output
	MessageChannel output();
}
*/


/*

@RestController
@RequestMapping("/reservations")
class ReservationServiceApiGatewayRestController {

	//private final MessageChannel channel;
	private final RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.POST)
	public void write(@RequestBody Reservation reservation) {
		//this.channel.send(MessageBuilder.withPayload(reservation.getReservationName()).build());
	}

	@Autowired
	public ReservationServiceApiGatewayRestController(
			@LoadBalanced RestTemplate restTemplate, ReservationChannels channels) {
		this.restTemplate = restTemplate;
		//this.channel = channels.output();
	}


			
			
	public Collection<String> fallback() {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping(method = RequestMethod.GET, value = "/names")
	public Collection<String> names() {
		return this
				.restTemplate
				.exchange("http://reservation-service/reservations",
						HttpMethod.GET, null,
						new ParameterizedTypeReference<Resources<Reservation>>() {
						})
				.getBody()
				.getContent()
				.stream()
				.map(Reservation::getReservationName)
				.collect(Collectors.toList());
	}

}


class Reservation {
	private String reservationName;

	public String getReservationName() {
		return reservationName;
	}
}
*/
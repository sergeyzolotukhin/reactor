package ua.in.sz.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;


@Slf4j
public class ReactorMain {
    public static void main(String[] args) throws InterruptedException {
        ConnectableFlux<Integer> publish = Flux.create(ReactorMain::intRange)
                .publishOn(Schedulers.parallel())
                .publish();

        publish.subscribe((c) -> log.info("Receive (1): {}", c));
        publish.subscribe((c) -> log.info("Receive (2): {}", c));

        publish.connect();

        Thread.sleep(1000);
    }

    private static void intRange(FluxSink<Integer> fluxSink) {
        for (int i = 0; i < 10; i++) {
//            log.info("Send: {}", i);
            fluxSink.next(i);
        }
    }
}

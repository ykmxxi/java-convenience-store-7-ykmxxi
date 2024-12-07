package store.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.service.ConvenienceFileService;

class ConvenienceFileClientTest {

    private ConvenienceFileClient convenienceFileClient;

    @BeforeEach
    void setUp() {
        convenienceFileClient = new ConvenienceFileClient(new ConvenienceFileReader(), new ConvenienceFileService());
    }

}

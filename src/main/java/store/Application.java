package store;

import store.presentation.client.ConvenienceClient;

public class Application {

    public static void main(String[] args) {
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        ConvenienceClient convenienceClient = new ConvenienceClient(
                configuration.inventoryClient(),
                configuration.outputView()
        );

        convenienceClient.run();
    }

}

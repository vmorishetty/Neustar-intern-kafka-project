public class Main {
    
    public static void main(String[] args) {
        // runs the producers and consumers

        //Sasl_SSLProducer p = new Sasl_SSLProducer();
        //p.producer();
        //Sasl_SSLConsumer c = new Sasl_SSLConsumer();
        //c.consumer();
        SimpleProducer p = new SimpleProducer();
        p.producer();
        SimpleConsumer2 c = new SimpleConsumer2();
        //c.consumer();
        /** 
        SSLProducer p = new SSLProducer();
        p.producer();
        SSLConsumer c = new SSLConsumer();
        c.consumer();
        SimpleProducer p = new SimpleProducer();
        p.producer();
        SimpleConsumer2 c = new SimpleConsumer2();
        c.consumer();
        */
    }
}
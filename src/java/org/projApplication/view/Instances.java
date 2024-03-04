package org.projApplication.view;

import org.projApplication.process.ProcessUnit;

public class Instances {

    public static class main1 extends MainApplication {
        public static void main(String[] args) {

            msg = "VAI SE FUDER PORRA";
            String[] neighours = {"127.0.0.2", "127.0.0.3"};
            instanceProcessUnit = new ProcessUnit("127.0.0.1", neighours);
            launch();
        }
    }

    public static class main2 extends MainApplication {
        public static void main(String[] args) {
            msg = "OLHA SÓ SE NÃO FUNCIONOU";
            launch();
        }
    }
}

package org.projApplication.view;

import org.projApplication.process.ProcessUnit;

public class Instances {
    public static class anel1 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.2", "127.0.0.4"};
            instanceProcessUnit = new ProcessUnit("127.0.0.1", neighours);
            launch();
        }
    }

    public static class anel2 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.1", "127.0.0.3"};
            instanceProcessUnit = new ProcessUnit("127.0.0.2", neighours);
            launch();
        }
    }

    public static class anel3 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.2", "127.0.0.1"};
            instanceProcessUnit = new ProcessUnit("127.0.0.3", neighours);
            launch();
        }
    }

    public static class anel4 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.3", "127.0.0.1"};
            instanceProcessUnit = new ProcessUnit("127.0.0.4", neighours);
            launch();
        }
    }

    public static class star1 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.2", "127.0.0.3", "127.0.0.4"};
            instanceProcessUnit = new ProcessUnit("127.0.0.1", "127.0.0.2", "127.0.0.3", "127.0.0.4");
            launch();
        }
    }

    public static class star2 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.1"};
            instanceProcessUnit = new ProcessUnit("127.0.0.2", "127.0.0.1");
            launch();
        }
    }

    public static class star3 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.2"};
            instanceProcessUnit = new ProcessUnit("127.0.0.3", "127.0.0.1");
            launch();
        }
    }

    public static class star4 extends MainApplication {
        public static void main(String[] args) {
            String[] neighours = {"127.0.0.3"};
            instanceProcessUnit = new ProcessUnit("127.0.0.4", "127.0.0.1");
            launch();
        }
    }

}


class Anel
{
    static class Main1{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.2", "127.0.0.4"};
            new Processo("127.0.0.1", neighbours);
        }
}

    static class Main2 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.3", "127.0.0.1"};
            new Processo("127.0.0.2", neighbours);
        }
}

    static class Main3 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.4", "127.0.0.2"};
            new Processo("127.0.0.3", neighbours);
        }
}

    static class Main4 {
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1", "127.0.0.3"};
            new Processo("127.0.0.4", neighbours);
        }
    }
}

class Estrela
{
    static class e1{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.2", "127.0.0.3", "127.0.0.4"};
            new Processo("127.0.0.1", neighbours);
        }
    }

    static class e2{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new Processo("127.0.0.2", neighbours);
        }
    }

    static class e3{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new Processo("127.0.0.3", neighbours);
        }
    }

    static class e4{
        public static void main(String[] args) {
            String[] neighbours = {"127.0.0.1"};
            new Processo("127.0.0.4", neighbours);
        }
    }

}

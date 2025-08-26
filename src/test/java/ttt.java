public class ttt {
    public class Boo {
        public Boo(int i) { }
        public Boo(String s) { }
        public Boo(String s, int i) { }
    }

    class SonOfBoo extends Boo {
        public SonOfBoo() {
            super("boo");
        }
    }
}

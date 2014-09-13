import java.util.Random;

public class LazySingleton {
    private int someField;
    private static LazySingleton instance;

    private LazySingleton() {
        this.someField = new Random().nextInt(200) + 1;         // (1)
    }

    public static LazySingleton getInstance() {
        if (instance == null) {                               // (2)
            synchronized (LazySingleton.class) {               // (3)
                if (instance == null) {                       // (4)
                    instance = new LazySingleton();           // (5)
                }
            }
        }
        return instance;                                      // (6)
    }

    public int getSomeField() {
        return this.someField;                                // (7)
    }
}

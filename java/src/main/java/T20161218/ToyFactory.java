package T20161218;

import java.util.HashMap;
import java.util.Map;

public class ToyFactory {
    /**
     * Your object will be instantiated and called as such:
     * ToyFactory tf = new ToyFactory();
     * Toy toy = tf.getToy(type);
     * toy.talk();
     */
    interface Toy {
        void talk();
    }

    class Dog implements Toy {
        @Override
        public void talk() {
            System.out.print("Wow");
        }
    }

    class Cat implements Toy {
        @Override
        public void talk() {
            System.out.print("Meow");
        }
    }

    private static Map<String, Class<? extends Toy>> classMap = new HashMap<>();

    static {
        classMap.put("cat", Cat.class);
        classMap.put("dog", Dog.class);
    }

    /**
     * @param type a string
     * @return Get object of the type
     */
    public Toy getToy(String type) {
        try {
            return classMap.get(type.toLowerCase()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}

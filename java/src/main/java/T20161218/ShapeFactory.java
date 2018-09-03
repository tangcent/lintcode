package T20161218;


import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    interface Shape {
        void draw();
    }

    class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.print(" ---- \n" +
                    "|    |\n" +
                    " ---- ");
        }
    }

    class Square implements Shape {
        @Override
        public void draw() {
            System.out.print(" ---- \n" +
                    "|    |\n" +
                    "|    |\n" +
                    " ---- ");
        }
    }

    class Triangle implements Shape {
        @Override
        public void draw() {
            System.out.print("  /\\\n" +
                    " /  \\\n" +
                    "/____\\");
        }
    }

    private static Map<String, Class<? extends Shape>> classMap = new HashMap<>();

    static {
        classMap.put("Rectangle", Rectangle.class);
        classMap.put("Square", Square.class);
        classMap.put("Triangle", Triangle.class);
    }

    /**
     * @param shapeType a string
     * @return Get object of type Shape
     */
    public Shape getShape(String shapeType) {
        try {
            return classMap.get(shapeType).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}

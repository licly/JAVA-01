import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * {@link ClassLoader} 示例
 *
 * @author licly
 * @date 2021/1/8
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = new HelloClassLoader().findClass("Hello");
        Method method = clazz.getDeclaredMethod("hello");
        method.invoke(clazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("Hello.xlass"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - (int)bytes[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }
}

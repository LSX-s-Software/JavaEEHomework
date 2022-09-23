import org.dom4j.DocumentException;
import org.example.BookService;
import org.example.MiniApplicationContext;
import org.example.exceptions.IllegalBeanException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MiniApplicationContextTest extends MiniApplicationContext {

    @Test
    public void testNormal() {
        assertDoesNotThrow(() -> {
            init("applicationContext.xml");
            BookService bookService = (BookService) getBean("bookService");
            bookService.save();
            getBean("bookDao");
            getBean("bookService");
        });
    }

    @Test
    public void testFileNotFound() {
        assertThrows(DocumentException.class, () -> {
            init("applicationContext_not_exist.xml");
            BookService bookService = (BookService) getBean("bookService");
            bookService.save();
        });
    }

    @Test
    public void testUnknownBean() {
        assertDoesNotThrow(() -> {
            init("applicationContext.xml");
            assertNull(getBean("foo"));
        });
    }

    @Test
    public void testInvalidBean() {
        assertDoesNotThrow(() -> {
            init("applicationContext_invalid_bean.xml");
        });
        assertThrows(IllegalBeanException.class, () -> {
            getBean("noClass");
        });
        assertThrows(ClassNotFoundException.class, () -> {
            getBean("invalidClass");
        });
        assertThrows(IllegalBeanException.class, () -> {
            getBean("noName");
        });
        assertThrows(IllegalBeanException.class, () -> {
            getBean("noValue");
        });
        assertThrows(IllegalBeanException.class, () -> {
            getBean("noRef");
        });
        assertThrows(IllegalBeanException.class, () -> {
            getBean("wrongType");
        });
    }

    @Test
    public void testInvalidClass() {
        assertDoesNotThrow(() -> {
            init("applicationContext_invalid_class.xml");
        });
        assertThrows(NoSuchMethodException.class, () -> {
            getBean("noSetter");
        });
        assertThrows(NoSuchMethodException.class, () -> {
            getBean("noInit");
        });
    }
}

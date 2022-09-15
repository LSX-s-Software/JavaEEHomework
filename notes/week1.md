# 第一周笔记

本周了解了Java 8的一些新特性，包括：

- Lambda 表达式：Lambda 允许把函数作为一个方法的参数（函数作为参数传递到方法中）。
- 方法引用：方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
- 新的函数式接口
- 接口的默认方法：默认方法就是一个在接口里面有了一个实现的方法。
- Stream API：新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。
- Date Time API：加强对日期与时间的处理。
- Optional 类：Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。
- Nashorn, JavaScript 引擎：Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。
- Base64：Java 8 内置了 Base64 编码的编码器和解码器。

## Lambda 表达式

lambda 表达式的语法格式如下：

```java
(parameters) -> expression
```

或者：

```java
(parameters) -> { statements; }
```

其中 parameters 声明可以不注明参数类型，expression 不需要声明返回值类型。

在 lambda 内部可以访问定义在作用域外的局部变量，但是不能修改；lambda 表达式的局部变量具有隐性的 final 的语义，不可被后面的代码修改。

## 方法引用

方法引用通过方法的名字来指向一个方法，个人理解为类似于C/C++里面的函数指针。语法如下：

- **构造器引用：**

  ```java
  final Car car = Car.create(Car::new);
  ```

- **静态方法引用：**

  ```java
  cars.forEach(Car::a_static_method);
  ```

- **特定类的任意对象的方法引用：**

  ```java
  cars.forEach(Car::a_method);
  ```

- **特定对象的方法引用：**

  ```java
  cars.forEach(police::follow);
  ```

## 新的函数式接口

函数式接口可以规定函数的参数类型和返回值类型，让其更好地支持 lambda 表达式。

JDK 1.8 之前已有的函数式接口:

- java.lang.Runnable
- java.util.concurrent.Callable
- java.security.PrivilegedAction
- java.util.Comparator
- java.io.FileFilter
- java.nio.file.PathMatcher
- java.lang.reflect.InvocationHandler
- java.beans.PropertyChangeListener
- java.awt.event.ActionListener
- javax.swing.event.ChangeListener

JDK 1.8 新增加的函数接口：

- java.util.function

java.util.function 包含了很多类，用来支持 Java的函数式编程，例如 `BiConsumer<T,U>` 代表了一个接受两个输入参数的操作，并且不返回任何结果。

## 接口的默认方法

当需要修改接口时候，需要修改全部实现该接口的类，目前的 java 8 之前的集合框架没有 foreach 方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。然而对于已经发布的版本，没法在不影响已有的实现的同时给接口添加新方法。所以引进的默认方法可以为了解决接口的修改与现有的实现不兼容的问题。

默认方法语法格式如下：

```java
public interface Vehicle {
	default void print() {
		System.out.println("我是一辆车!");
	}
}
```

但有时候一个类实现了多个接口，且这些接口有相同的默认方法：

```java
public interface Vehicle {
  default void print() {
    System.out.println("我是一辆车!");
  }
}

public interface FourWheeler {
  default void print() {
    System.out.println("我是一辆四轮车!");
  }
}
```

第一个解决方案是创建自己的默认方法，来覆盖重写接口的默认方法：

```java
public class Car implements Vehicle, FourWheeler {
  default void print() {
    System.out.println("我是一辆四轮汽车!");
  }
}
```

第二种解决方案可以使用 super 来调用指定接口的默认方法：

```java
public class Car implements Vehicle, FourWheeler {
	public void print() {
    Vehicle.super.print();
  }
}
```

## Optional 类

Optional 类是一个可以为`null`的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样就不用显式进行空值检测。Optional 类的引入很好地解决空指针异常。

```java
Integer value1 = null;
Integer value2 = new Integer(10);
        
// Optional.ofNullable - 允许传递为 null 参数
Optional<Integer> a = Optional.ofNullable(value1);

// Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
Optional<Integer> b = Optional.of(value2);

// Optional.orElse - 如果值存在，返回它，否则返回默认值
Integer value1 = a.orElse(new Integer(0));

//Optional.get - 获取值，值需要存在
Integer value2 = b.get();
```

## Stream

Java 8 API添加了一个新的抽象称为流Stream，可以让程序员以一种声明的方式处理数据。Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象，有点像 C# Linq语句的风格。例如要完成筛选、排序、map操作可以这样写：

```java
List<Integer> transactionsIds = widgets.stream()
          .filter(b -> b.getColor() == RED)
          .sorted((x,y) -> x.getWeight() - y.getWeight())
          .mapToInt(Widget::getWeight)
          .sum();
```


INSERT INTO themes (title) VALUES
    ('Java Core'),
    ('Spring Boot'),
    ('SQL');

INSERT INTO folders (title, theme_id) VALUES
    ('Основы', 1),
    ('Коллекции', 1),
    ('REST API', 2),
    ('JPA', 2),
    ('SELECT', 3);

INSERT INTO cards (title, description, code, folder_id) VALUES
    (
        'Что такое JVM?',
        'JVM (Java Virtual Machine) — виртуальная машина, которая выполняет байткод Java и обеспечивает кроссплатформенность.',
        'public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, JVM!");
    }
}',
        1
    ),
    (
        'Чем отличается == от equals?',
        'Оператор == сравнивает ссылки (для объектов) или значения (для примитивов). Метод equals() сравнивает содержимое объектов.',
        'String a = new String("hi");
String b = new String("hi");
System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true',
        1
    ),
    (
        'Что такое ArrayList?',
        'ArrayList — динамический массив из java.util. Быстрый доступ по индексу, медленные вставки/удаления в середину.',
        'List<String> list = new ArrayList<>();
list.add("Java");
list.add("Kotlin");
System.out.println(list.get(0));',
        2
    ),
    (
        'Как объявить REST-контроллер?',
        '@RestController объединяет @Controller и @ResponseBody. Методы возвращают тело ответа напрямую.',
        '@RestController
@RequestMapping("/api/cards")
public class CardController {

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return cardService.getById(id);
    }
}',
        3
    ),
    (
        'Что делает @Entity?',
        'Аннотация @Entity помечает класс как JPA-сущность, которая отображается на таблицу в БД.',
        '@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}',
        4
    ),
    (
        'Как выбрать все строки?',
        'SELECT * возвращает все столбцы таблицы. На практике лучше явно перечислять нужные поля.',
        'SELECT id, title
FROM themes
ORDER BY title;',
        5
    );

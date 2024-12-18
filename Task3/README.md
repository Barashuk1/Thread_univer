# Проєкт: Паралельна обробка даних з використанням потоків

## Опис

Цей проєкт демонструє використання паралельного програмування для обробки великих масивів даних та роботи з файловою системою. Реалізовано два завдання:
1. **Попарна сума елементів масиву**.
2. **Пошук файлів певного формату в директорії**.

Для обробки задач використано підходи **Work Stealing** і **Work Dealing**. Програма вимірює час виконання кожного підходу та порівнює результати.

## Завдання

### Завдання 1: Попарна сума елементів масиву
- Генерація великого масиву випадкових чисел заданого розміру.
- Розбиття масиву на частини для паралельної обробки.
- Підрахунок попарної суми елементів масиву.
- Порівняння підходів **Work Stealing** та **Work Dealing**.

### Завдання 2: Пошук файлів певного формату
- Рекурсивний пошук файлів у заданій директорії.
- Підрахунок файлів з певним розширенням.
- Порівняння підходів **Work Stealing** та **Work Dealing**.

## Функціональні можливості
- **Паралельна обробка даних**: Використання потоків для обробки масивів та пошуку файлів.
- **Порівняння продуктивності**: Час виконання для кожного підходу вимірюється та виводиться.
- **Гнучкість**: Користувач може задати параметри для розміру масиву та директорії.

## Технології
- **Java 11+** для реалізації багатопотокових завдань.
- **ExecutorService** для керування потоками.
- **Fork/Join Framework** для роботи з паралельними задачами.
- **Java NIO** для роботи з файловою системою.

## Вхідні параметри
- **Розмір масиву**: Розмір масиву та діапазон випадкових чисел можна змінити у класі `task1.java`.
- **Директорія для пошуку**: Шлях до директорії та тип файлів (наприклад, *.pdf*) задається у класі `task2.java`.

## Порівняння продуктивності

| Завдання             | Підхід         | Час виконання (мс) |
|----------------------|----------------|--------------------|
| Попарна сума         | Work Stealing  | 6                  |
| Попарна сума         | Work Dealing   | 9                  |
| Пошук файлів         | Work Stealing  | 295                |
| Пошук файлів         | Work Dealing   | 301                |

## Висновки
- **Work Stealing** працює швидше для задач із непередбачуваним часом виконання підзадач.
- **Work Dealing** є більш стабільним і простим для задач із рівномірним розподілом навантаження.

## Ліцензія
Цей проєкт має відкриту ліцензію. Ви можете використовувати та змінювати код на свій розсуд.

---

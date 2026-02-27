#  Java Toy Language Interpreter

![Java](https://img.shields.io/badge/Java-11%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-GUI-474A54?style=for-the-badge&logo=java&logoColor=white)
![Design Patterns](https://img.shields.io/badge/Design_Patterns-MVC_%7C_Command-success?style=for-the-badge)

A fully functional, concurrent Toy Language Interpreter built from scratch in Java. This project demonstrates core computer science concepts including **lexical scoping, heap memory management, garbage collection, and multithreaded execution**. It includes a Graphical User Interface (GUI) built with JavaFX.

## 🎯 Project Focus & Skills Demonstrated

I built this project to deepen my understanding of internal language mechanisms and advanced object-oriented programming. 

* **Concurrency & Synchronization:** Implemented multithreaded execution using Java `ExecutorService` and Futures. Designed and integrated advanced synchronization controls to prevent race conditions, including **Locks, Semaphores, Cyclic Barriers, and CountDownLatches**.
* **Memory Management:** Developed a custom Heap data structure and implemented a **Garbage Collector** that dynamically frees unreferenced memory.
* **Type Checking:** Implemented a static type-checking mechanism phase to validate program semantics before execution.
* **Design Patterns:** Heavily utilized Software Engineering patterns including **Model-View-Controller (MVC)** and **Command** patterns to ensure decoupled and maintainable code.

## ✨ Language Features

The toy language supports a variety of standard programming constructs:
* **Primitive Types:** `int`, `bool`, `string`
* **Control Flow:** `If-Else` statements, `While` loops, `For` loops
* **Variables & Memory:** Variable declarations, assignments, and dynamic heap allocation (`new`, `readHeap`, `writeHeap`).



* **Concurrency:** `fork` statements to create child threads. Threads share the Heap and File Table but maintain their own Execution Stack and Symbol Table.
* **Thread Coordination:** Built-in language support for advanced thread synchronization primitives (e.g., `lock`/`unlock`, `acquire`/`release` for Semaphores, `await` for CountDownLatch, and Barrier statements).
* **File I/O:** Reading from and writing to files directly within the language.
* **Relational & Arithmetic Operations:** Full expression evaluation suite.

## 🖥️ Graphical User Interface (GUI)

The project features a comprehensive JavaFX dashboard that allows developers to step through the code execution in real-time. It provides a visual representation of the internal state of the program.

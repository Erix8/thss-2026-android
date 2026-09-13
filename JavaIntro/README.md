# ☕ Homework 2 — A Tiny Student Management System

Three classes, one text file, and the whole "hello, Java" journey in a single afternoon: **control flow**,
**arrays**, **OOP**, and **file I/O**. Nothing here is clever — it's just me learning to stop forgetting
semicolons. 😅

## 🎭 The Cast

| Class | Role 🎬 |
|-------|---------|
| [`Student.java`](Student.java) | The data holder — `name`, `age`, `score`, a constructor, getters, and `printInfo()` |
| [`StudentManager.java`](StudentManager.java) | The brain — add/print students, average & top score, save/load `students.txt` |
| [`Main.java`](Main.java) | The stage — arithmetic + array helpers, four test methods, and the `main` that kicks things off |

## 🧩 What's inside

### 1. ✖️ Arithmetic on two integers
Add, subtract, multiply, divide — with a guard so dividing by zero prints a friendly warning instead of
blowing up. The console test wraps `nextInt()` in a `try/catch` so typing letters doesn't crash anything. 🛡️

### 2. 📊 Array statistics
Max, min, and average of an `int[]`, all done with plain `for` loops (no `Arrays`/`Collections` shortcuts
allowed). Reads 5 numbers from the console and prints the average to 2 decimals.

### 3. 🧱 Student management (OOP)
`Student` keeps its fields `private` and exposes them through getters; `StudentManager` owns a
`List<Student>` and answers the questions: *Who's in the list? What's the average? Who scored highest?* 🏆

### 4. 💾 Persistence (file I/O)
Round-trip the whole list through `students.txt`, one student per line as `name,age,score`. The catch: the
load side has to survive missing files, empty files, wrong field counts, and non-numeric garbage. 🧯

## 🛩️ Takeoff

1. Make sure you have **JDK 8+** (`java -version` should say something). ☕
2. Compile everything in this folder:
   ```bash
   javac *.java
   ```
   *(the generated `.class` files are git-ignored — see [`.gitignore`](.gitignore))*
3. Run it:
   ```bash
   java Main
   ```
4. Want a different demo? Open [`Main.java`](Main.java) and flip the comments in `main`:
   ```java
   public static void main(String[] args) {
       testCalculate();        // arithmetic + console input
       // testArrayStatistics(); // max / min / average
       // testOOP();            // student management (OOP)
       // testFileIO();         // save → clear → load
   }
   ```
   By default only `testFileIO()` runs. 🎯

> ⚠️ `students.txt` is written to the **current working directory**, so run `java Main` from inside this
> folder or the file will land somewhere unexpected. 📂

## 👀 What it looks like

Running the file I/O demo (`java Main`) prints roughly:

```
===== Start File I/O Test (Persistence) =====
Step 1: Create student objects...
Created 4 student objects.

Step 2: Save to file...
Successfully saved 4 students to students.txt

Step 3: Clear memory data...
Student data in memory has been cleared!
Memory data check: 0 students left.

Step 4: Load from file...
Successfully loaded 4 students from students.txt

Step 5: Output recovered student data...
===== All Student Information =====
Name: Alice, Age: 20, Score: 90.0
Name: Bob, Age: 21, Score: 85.5
Name: Charlie, Age: 19, Score: 92.0
Name: David, Age: 20, Score: 88.0
===================================
Recovered Average Score: 88.88
Recovered Student with Highest Score: Name: Charlie, Age: 19, Score: 92.0
=============================================
```

## 🧯 Things that could go wrong (and don't)

| Scenario | What happens 💬 |
|----------|----------------|
| Divide by zero | Prints `Cannot calculate! Divisor cannot be 0.` and moves on |
| Non-integer typed at the prompt | Caught by `try/catch`, friendly message, no crash |
| `students.txt` missing | `Load failed! File students.txt does not exist.` |
| `students.txt` empty | `Load failed! File students.txt is empty.` |
| Line with ≠ 3 fields | Skipped with `Skip invalid line: ... (format error)` |
| Age/score not a number | `NumberFormatException` caught, error message printed |
| Read/write failure | `IOException` caught, reason printed |

## 💭 Notes & takeaways

*(Written as I went.)*

- `try-with-resources` is the nicest thing in Java so far — no more forgetting to `close()`. 🧹
- `(double) sum / n` really matters; integer division silently truncates and looks like a bug. 🤯
- Checking `fields.length` and `file.length()` **before** parsing saved me from a lot of stack traces.
- Returning `null` for "no students" means every caller has to null-check — fine here, awkward later. 🤔

## 🗂️ File map

| File | What it is |
|------|-----------|
| [`Student.java`](Student.java) | entity class 🧱 |
| [`StudentManager.java`](StudentManager.java) | management + file I/O logic 🧠 |
| [`Main.java`](Main.java) | entry point + four test methods 🎬 |
| [`students.txt`](students.txt) | sample persisted data (`name,age,score`) 💾 |

---

All four test paths pass. On to the next homework! 🏁

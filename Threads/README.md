# 🧵 Homework 6 — Producer / Consumer, Done Properly

Two buttons, one shop shelf, and a small mob of threads fighting over it. The whole point: a bounded
inventory shared by many producers and consumers, where **nothing is ever sold twice, nothing is sold
before it exists, and nobody deadlocks.** 🔒

## 🎛️ The shared state

Everything lives in `ProAndCon`, a class nested inside
[`MainActivity.java`](app/src/main/java/com/example/threads/MainActivity.java):

| Field | Meaning | Value here |
|-------|---------|-----------|
| `queue` | The shelf — item IDs currently in stock | starts empty 📦 |
| `max` | Shelf capacity | **5** |
| `count` | Items produced so far; also the next ID | starts at 0 |
| `stop` | Total production target — stop after this many | **20** |
| `n` | Max items one producer burst may make | **2** |

Wired up in `onCreate()` as `new ProAndCon(5, 20)`, with:

- `addProducer()` → `new Thread(new producer(2000, 2)).start()` — bursts of 2, every 2s 🏭
- `addConsumer()` → `new Thread(new consumer(1000)).start()` — one item, every 1s 🛒

Hit each button repeatedly and you get several of each, all sharing that one `queue`.

## 🔒 One lock, three rules

1. **One lock for everything**: every enqueue and dequeue happens inside `synchronized (queue)`. No
   partially-visible mutations, no two threads touching stock at once.
2. **`wait()` in a `while` loop, never an `if`**: the condition is re-checked after every wake-up, so
   spurious wake-ups and lost races can't slip through.
3. **`notifyAll()` after every state change**: producers and consumers both wait on the same monitor, so
   waking *all* of them (not one) is what keeps the pipeline moving.

## 🏭 `produce()`

1. **Full shelf?** While `queue.size() == max && count < stop`, `wait()` — block until a consumer frees space. 😴
2. **Done already?** If `count >= stop`, `notifyAll()` and return.
3. **How many this round?** Clamp against three ceilings at once:
   ```java
   int capacity  = max - queue.size();
   int toProduce = Math.min(n, capacity);
   toProduce     = Math.min(toProduce, stop - count);
   ```
   So a burst never overflows the shelf, never overshoots the target, and — on a nearly-full shelf — tops
   up to exactly `max` instead of doing nothing.
4. **Make them one by one**: `int id = ++count; queue.add(id);` — IDs run 1, 2, 3… with no gaps and no
   duplicates, because the increment only ever happens under the lock.
5. **Log and wake**: buffer the lines, append them, then `notifyAll()`. 📢

## 🛒 `consume()`

1. **Empty shelf?** Loop while `queue.isEmpty()` — but check `count >= stop` *inside* the loop first. Once
   production is over, waiting forever would be a deadlock, so it wakes everyone and returns instead. 🦺
2. **Take one item**: `int id = queue.remove(0);` — only produced IDs ever sit in the queue, and removal
   takes the item out of stock, so the same item can't be sold twice.
3. **Log and wake**: same pattern, `notifyAll()` at the end.

## 🖥️ The screen

[`activity_main.xml`](app/src/main/res/layout/activity_main.xml) is deliberately minimal: two buttons
(`addProducer` / `addConsumer`, wired with `android:onClick`) above a `320dp` `TextView` with vertical
scrollbars and generous `lineSpacingExtra`.

Because worker threads never touch UI, `appendLogs()` marshals through the main thread — and batches a
whole burst into **one** post:

```java
MainActivity.this.runOnUiThread(() -> {
    for (String log : logs) textView.append(log);
});
```

Console output still happens via `System.out.println` (the thread's name, printed once on start). 🖨️

## 📜 Sample log

```text
Thread-1 Produce: 7 Remain: 3
Thread-2 Consume: 5 Remain: 2
```

Read it as: *thread 1 produced item 7, leaving 3 in stock; thread 2 consumed item 5, leaving 2.* Note that
`Remain` is **how much is on the shelf**, not how much is left to produce. 🧮

## ✅ Why this satisfies the brief

- **Many producers + many consumers** — all synchronized on the one `queue` monitor.
- **Full-shelf wait / empty-shelf wait** — guaranteed by `while` + `wait()`.
- **Batched production of `n`** — `n` is a constructor argument, further clamped by `capacity` and `stop`.
- **IDs start at 1, contiguous and unique** — `++count` happens exactly once per item, under the lock.
- **No double-selling / no selling the unproduced** — consumers only ever `remove(0)` from `queue`.
- **Threads stop hitting `stop`** — the producer path exits and wakes everyone so waiters can finish.
- **Required log format** — `<thread> Produce|Consume: <id> Remain: <stock>`, appended straight to the `TextView`.

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`MainActivity.java`](app/src/main/java/com/example/threads/MainActivity.java) | `ProAndCon`, `producer`, `consumer`, the buttons, and the log sink 🧠 |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Two buttons + the scrolling log view |
| [`strings.xml`](app/src/main/res/values/strings.xml) | Just `app_name` — everything else is inline 🔤 |

## 🚀 Run it

1. Open this `Threads` folder in **Android Studio** and let Gradle sync. 📂
2. Run it, then tap **addProducer** / **addConsumer** a few times to build up the crowd. ▶️
3. Watch the log — producers stall at 5 stock, consumers stall at 0, and everything drains cleanly at 20. 📈

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` | 24 / 36 |
| AGP | 9.1.0 |
| Activity | 1.12.4 |

## 💭 Notes & takeaways

*(Written while building it.)*

- ⚠️ The producer's outer `while (count < stop)` reads `count` **without** holding the lock. It's a real
  (if benign here) data race — it works only because `produce()` re-checks the same condition inside the
  monitor. Moving the loop condition under the lock would make it airtight.
- The `count >= stop` check in the **consumer's** empty-shelf branch is the unsung hero — without it,
  consumers would wait forever after the last item was made. Deadlock guards often live in the *other*
  thread's code. 🦺
- `queue.remove(0)` on an `ArrayList` is O(n) per sale. An `ArrayDeque` or `LinkedList` is the natural
  choice for a FIFO; with a shelf capacity of 5 it just doesn't matter.
- Batching logs into a `List<String>` and posting once is a habit worth keeping — one UI-thread hop per
  burst instead of one per item. 🚀
- `producer`/`consumer` are non-static inner classes, so they can call `appendLogs` straight up the chain
  to `MainActivity`. Handy, though it does hide the coupling. 🪆

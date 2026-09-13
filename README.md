# 📱 My Solutions to Android(Java) homeworks
Personal solutions to the programming assignments of *Introduction to Mobile Application Development*
(44100612), Spring 2026 — THSS. Nine folders, nine homeworks, and a trail of bugs I'll never forget. 🐛

## 🗺️ The assignments

| HW# | 📝 Task | 🎯 What it taught me |
| :---: | :--- | :--- |
| 02 | ☕ [Java Intro](./JavaIntro/README.md) | Core Java: control flow, arrays, OOP, and file I/O in one small student manager |
| 03 | 📋 [ForumActivity](./ForumActivity/README.md) | Two Activities, one Intent, and a list→detail flow |
| 04 | 💬 [WechatContact](./WechatContact/README.md) | A multi-view-type RecyclerView plus a custom A–Z sidebar `View` |
| 05 | 🧭 [SimpleTab](./SimpleTab/README.md) | ViewPager2, Fragments, and a tab switcher built by hand |
| 06 | 🧵 [Threads](./Threads/README.md) | Producer/consumer with `synchronized`, `wait()`, and `notifyAll()` |
| 07 | ✅ [MyToDoList](./MyToDoList/README.md) | RecyclerView CRUD + Gson persistence + notifications |
| 08 | 🧠 [Prompting](./Prompting/README.md) | Six prompting patterns, compared against a local model |
| 09 | 🧮 [MCP Server](./MCPServer/README.md) | A FastMCP server exposing four tools over MCP |
| 10 | 🎓 [Campus Assistant](./CampusAssistant/README.md) | `BottomNavigationView` swapping Fragments |

## 🧰 The shared Android baseline

Every app folder here is a standard Gradle project built the same way:

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` / `compileSdk` | 24 / 36 / 36 |
| Build scripts | Kotlin DSL (`.kts`) + `gradle/libs.versions.toml` 📦 |
| AGP | 9.1.0 on the newer projects, 8.13.2 on Homework 3 |

To run any of them: open the folder that contains `settings.gradle.kts` in Android Studio, let Gradle sync,
and hit **Run** ▶️. The wrapper and version catalog are pinned, so there's nothing to configure by hand.

## 🐍 The two that aren't Android

- [**Prompting**](./Prompting/README.md) — Python + Ollama, six self-grading prompt experiments. 🧠
- [**MCP Server**](./MCPServer/README.md) — Python + FastMCP, a student-score server tested in-process. 🧮

Their READMEs cover their own dependencies and how to run them.

---

Still standing after all nine. 🏁
# 🧮 Homework 9 — A Student Score MCP Server

A tiny [MCP](https://modelcontextprotocol.io) server built with **FastMCP** that answers questions about a
class roster: averages, the top scorer, who's failing, and lookups by student ID. Four tools, one JSON
file, no network required to test. 🔌

## 🚪 The four tools

| Tool | Signature | What it does |
|------|-----------|--------------|
| `calculate_average` | `(scores: List[float]) -> float` | Average of an arbitrary score list; raises on empty ➗ |
| `get_top_student` | `() -> Dict` | Reads the file, returns the highest **usual + final** 🏆 |
| `get_failing_students` | `() -> List[Dict]` | Everyone whose total is under the pass mark ⚠️ |
| `get_student_by_id` | `(student_id: str) -> Dict` | Exact-match lookup; raises if empty or missing 🔎 |

Registered with the `@mcp.tool()` decorator — the type hints are the schema, so the client sees a proper
`List[float]` input instead of a loose string. 🧩

## 📂 The data

[`data/students.json`](data/students.json) is a flat array:

```json
[
  {"name": "Alice", "student_id": "1001", "usual_score": 85, "final_score": 90}
]
```

| Name | ID | Usual | Final | **Total** |
|------|----|-------|-------|-----------|
| Alice | 1001 | 85 | 90 | 175 |
| Bob | 1002 | 70 | 60 | 130 |
| Charlie | 1003 | 50 | 40 | **90** ⚠️ |
| David | 1004 | 95 | 98 | **193** 🥇 |

Totals are out of 200, so the 60% pass line lands at **120** — which makes Charlie the only failing student.

## 🧩 How it's wired

- `load_data()` opens the JSON with `encoding="utf-8"` and raises `FileNotFoundError` if the file is
  missing — errors surface to the caller instead of returning a silent empty list. 🚨
- [`server.py`](server.py) is the whole server: `FastMCP("Student Score Server")` plus four decorated
  functions, ~80 lines total.
- [`test_server.py`](test_server.py) drives it with `fastmcp.Client` **in-process** — `from server import mcp`
  then `async with Client(mcp)`. No ports, no subprocesses. 🧪

## 📜 A passing run

```text
Available tools: ['calculate_average', 'get_top_student', 'get_failing_students', 'get_student_by_id']

=== Normal Calls ===
Average: 90.0
Top student: {'name': 'David', 'student_id': '1004', 'usual_score': 95, 'final_score': 98}
Failing students: [Root()]
Student 1001: {'name': 'Alice', 'student_id': '1001', 'usual_score': 85, 'final_score': 90}

=== Exception Tests ===
Empty list error: Error calling tool 'calculate_average': Score list cannot be empty.
Invalid ID error: Error calling tool 'get_student_by_id': Student ID cannot be empty.
Not found error: Error calling tool 'get_student_by_id': Student not found.
```

- `Average: 90.0` ✅ — `(80 + 90 + 100) / 3`.
- `Top student` ✅ — David, total 193.
- `Student 1001` ✅ — round-trips the Alice record.
- All three failure paths raise clean, readable messages instead of returning `None`/`[]`. 🛡️

## ⚠️ Three things worth knowing

1. **`Failing students: [Root()]` is not a bug.** FastMCP wraps list results in a Pydantic `Root` object, so
   printing it directly shows the wrapper rather than its contents — the underlying payload is the
   `List[Dict]` (here, just Charlie). 🔍
2. **A docstring disagrees with the code.** `get_failing_students` says *"below 60"* but compares
   `total < 120`, because the score is out of 200. The code and the 120 threshold are right; the docstring
   is a leftover from the single-score version. Trust the `if`. 🤨
3. **`DATA_FILE` is a relative path** — `os.path.join("data", "students.json")`. Run the script from inside
   `MCPServer/`, or `load_data()` raises `FileNotFoundError` no matter how correct the JSON is. 📁

## 🛠️ Setup & run

```bash
pip install -r requirements.txt
python test_server.py
```

[`requirements.txt`](requirements.txt) is a **full pinned freeze** (~85 packages, `fastmcp==2.14.5` at its
core), so installation is reproducible — and noticeably slower than installing FastMCP alone. ⏳

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`server.py`](server.py) | The MCP server: 4 tools + the JSON loader 🧠 |
| [`test_server.py`](test_server.py) | In-process client, normal calls + 3 failure cases 🧪 |
| [`data/students.json`](data/students.json) | The roster 📂 |
| [`requirements.txt`](requirements.txt) | Pinned dependency freeze 📌 |

## 💭 Notes & takeaways

- `@mcp.tool()` + type hints means **the schema is free** — I never wrote a JSON Schema by hand, and the
  client still got a typed `List[float]` argument. That's the nicest part of the framework. ✨
- Validating at the boundary (empty list, empty ID) and raising `ValueError` gives the model a crisp,
  actionable error — much better than returning `0.0` or `None` and letting the LLM guess. 🎯
- Testing an MCP server needs no transport: the server is just an object, and `Client(mcp)` talks to it
  directly. Async `await client.call_tool(...)` is the only twist. 🌐
- The three tools that read the file re-open it on every call. Fine for a demo, but a cached load (or a
  proper data layer) is where this would go next. 💾

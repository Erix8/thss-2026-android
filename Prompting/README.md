# 🧠 Homework 8 — Prompting Patterns, Six Ways

Six standalone Python scripts, each isolating **one** prompting technique against a locally-running model
(`llama3.1:8b` via Ollama). No frameworks, no agents — just a system prompt, a runner, and a pass/fail
check, so the *prompt* is the only thing that changes between experiments. 🎛️

## 🗺️ The six experiments

| Script | Technique | Task | Passes when |
|--------|-----------|------|-------------|
| [`k_shot_prompting.py`](k_shot_prompting.py) | k-shot (4 worked examples) | Reverse `httpstatus` → `sutatsptth` | Any of 5 runs matches 🔁 |
| [`chain_of_thought.py`](chain_of_thought.py) | Chain-of-thought | `3^12345 mod 100` | Any of 5 runs yields `Answer: 43` 🪜 |
| [`self_consistency_prompting.py`](self_consistency_prompting.py) | Self-consistency (majority vote) | Henry's 60-mile bike trip | The **mode** of 5 answers is `Answer: 25` 🗳️ |
| [`rag.py`](rag.py) | Retrieval-augmented generation | Write `fetch_user_name()` from the bundled [`data/api_docs.txt`](data/api_docs.txt) | Generated code contains every required snippet 📚 |
| [`reflexion.py`](reflexion.py) | Reflexion (generate → test → reflect → fix) | `is_valid_password()` | All 4 test cases pass after one reflection 🔄 |
| [`tool_calling.py`](tool_calling.py) | Tool calling | Emit a JSON tool call | Tool output equals the expected listing 🛠️ |

## 🧩 How each one works

**The shared skeleton.** Every script follows the same shape: fill in a `YOUR_SYSTEM_PROMPT` (or
`YOUR_REFLEXION_PROMPT` / `YOUR_CONTEXT_PROVIDER`), and `test_your_prompt()` runs it up to `NUM_RUNS_TIMES`
and prints `SUCCESS` on a pass. Swapping prompts never means touching the harness. 🧱

**Answer extraction.** The math scripts find the **last** line matching `answer: ...` and normalize it to
`Answer: <number>` (stripping commas, preferring the first number). Verbose reasoning is therefore fine —
only the final line has to be right. ✂️

**Temperature is part of the design.** Self-consistency runs hot (`temperature: 1`) *on purpose*, because
it needs diverse samples to take a majority vote over. The others stay cool — `0.0` for RAG, `0.2` for
reflexion, `0.3` for CoT and tool calling — to keep answers repeatable. 🌡️

**RAG in two halves.** `load_corpus_from_files()` reads [`data/api_docs.txt`](data/api_docs.txt) — a small API
reference covering the **base URL**, the **`X-API-Key`** auth header, the **`GET /users/{id}`** endpoint, and
the **`{"id", "name"}`** response shape. Then `YOUR_CONTEXT_PROVIDER(corpus)` picks which docs to ship, and
the selection is injected into the user message as a `Context:` block. Grading is snippet-based rather than
exact-match, so the model just has to produce code containing `def fetch_user_name(`, `requests.get`,
`/users/`, `X-API-Key`, and `return`. 📚

**Reflexion actually executes the fix.** The model's code is `exec()`'d into a namespace and run against a
4-case suite; failures get turned into a diagnostic message (including the *specific* rule that broke) which
is fed back for one repair iteration. 🩺

**Tool calling is self-grading.** The expected output isn't hardcoded — `compute_expected_output()` walks
this file with `ast` and lists every top-level function's return type, so the check can't drift.

## ⚙️ Setup & run

You need **Ollama** serving `llama3.1:8b` locally, plus the two Python packages:

```bash
ollama pull llama3.1:8b
pip install ollama python-dotenv
python k_shot_prompting.py     # or any of the other five
```

`load_dotenv()` is called in every script, so an `.env` file can carry `OLLAMA_HOST` — there just isn't one
committed to the repo. 🔌

Nothing extra to download, either: [`data/api_docs.txt`](data/api_docs.txt) already ships with the repo, so
`rag.py` has its corpus waiting for it. 📚

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`k_shot_prompting.py`](k_shot_prompting.py) | Worked examples as the spec 📎 |
| [`chain_of_thought.py`](chain_of_thought.py) | Stepwise modular arithmetic 🪜 |
| [`self_consistency_prompting.py`](self_consistency_prompting.py) | Sample-and-vote with `Counter` 🗳️ |
| [`rag.py`](rag.py) | Corpus loading, context selection, snippet validation 📚 |
| [`data/api_docs.txt`](data/api_docs.txt) | The retrieval corpus: base URL, auth header, endpoint, response shape 📄 |
| [`reflexion.py`](reflexion.py) | The only file with an actual test suite 🧪 |
| [`tool_calling.py`](tool_calling.py) | AST-based tool, JSON parsing, dispatch table 🛠️ |

## 💭 Notes & takeaways

*(Written while building it.)*

- ✅ **`rag.py` now retrieves for real.** [`data/api_docs.txt`](data/api_docs.txt) is in the repo, so the
  corpus loads cleanly, `YOUR_CONTEXT_PROVIDER` ships an actual document, and the prompt gets a real
  `Context:` block instead of `(no context provided)`. 📁
- That docs path is built from `os.path.dirname(__file__)`, so RAG loads its corpus no matter which directory
  you launch it from — unlike the sibling MCP server, which uses a relative `data/…` path and must be run from
  its own folder. 🔑
- Prompt-vs-harness separation is the whole point: the same `test_your_prompt()` grades a weak prompt and a
  strong one, which is what makes the numbers comparable across the six files. 📊
- `while`-style retry loops (`NUM_RUNS_TIMES`) are a blunt substitute for measuring variance — running 5×
  and passing on **any** success hides how often the prompt actually fails. Counting successes would be more
  honest. 🤔
- `reflexion.py` calls `exec()` on model-generated code. It's fine for a controlled exercise (and the script
  says as much), but it is genuinely arbitrary code execution — never in anything that faces real input. 🚨
- Several files still carry a `# TODO: Fill this in!` comment directly above a prompt that **is** filled in.
  Leftovers from the template — harmless, just noise. 🧹

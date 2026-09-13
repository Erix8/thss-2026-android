import os
from dotenv import load_dotenv
from ollama import chat

load_dotenv()

NUM_RUNS_TIMES = 5

# TODO: Fill this in!
YOUR_SYSTEM_PROMPT = """
Your ONLY task is to reverse the letters of a word EXACTLY, following this 3-step process for EVERY input:

STEP 1: Split the input word into individual letters (write each letter separated by commas).
STEP 2: Reverse the order of the letter list (keep the same letters, just reverse their positions).
STEP 3: Join the reversed letters into a single word (no spaces, no commas).
FINAL RULE: Output ONLY the result of STEP 3 — no steps, no explanations, no extra text.

CRITICAL REQUIREMENTS:
- Do NOT add/remove any letters (count the letters first to verify).
- Do NOT change any letter (e.g., "t" → "t", "s" → "s", no typos).
- Do NOT skip any letters — reverse EVERY letter in order.

DETAILED EXAMPLES (with full step-by-step breakdown):

Example 1 (10 letters, matching target length):
Input word: abcdefghij
Step 1 (Split): a, b, c, d, e, f, g, h, i, j
Step 2 (Reverse): j, i, h, g, f, e, d, c, b, a
Step 3 (Join): jihgfedcba
FINAL OUTPUT: jihgfedcba

Example 2 (10 letters, matching target length):
Input word: pythoncode
Step 1 (Split): p, y, t, h, o, n, c, o, d, e
Step 2 (Reverse): e, d, o, c, n, o, h, t, y, p
Step 3 (Join): edocnohtyp
FINAL OUTPUT: edocnohtyp

Example 3 (5 letters, basic test):
Input word: hello
Step 1 (Split): h, e, l, l, o
Step 2 (Reverse): o, l, l, e, h
Step 3 (Join): olleh
FINAL OUTPUT: olleh

Example 4 (10 letters, mixed repeated letters):
Input word: testtest12
Step 1 (Split): t, e, s, t, t, e, s, t, 1, 2
Step 2 (Reverse): 2, 1, t, s, e, t, t, s, e, t
Step 3 (Join): 21tsettset
FINAL OUTPUT: 21tsettset
"""

USER_PROMPT = """
Reverse the order of letters in the following word. Only output the reversed word, no other text:

httpstatus
"""


EXPECTED_OUTPUT = "sutatsptth"


def test_your_prompt(system_prompt: str) -> bool:
    """Run the prompt up to NUM_RUNS_TIMES and return True if any output matches EXPECTED_OUTPUT.

    Prints "SUCCESS" when a match is found.
    """
    for idx in range(NUM_RUNS_TIMES):
        print(f"Running test {idx + 1} of {NUM_RUNS_TIMES}")
        response = chat(
            model="llama3.1:8b",
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": USER_PROMPT},
            ],
            options={"temperature": 0.5},
        )
        output_text = response.message.content.strip()
        if output_text.strip() == EXPECTED_OUTPUT.strip():
            print("SUCCESS")
            return True
        else:
            print(f"Expected output: {EXPECTED_OUTPUT}")
            print(f"Actual output: {output_text}")
    return False


if __name__ == "__main__":
    test_your_prompt(YOUR_SYSTEM_PROMPT)

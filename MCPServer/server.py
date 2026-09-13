from fastmcp import FastMCP
from typing import List, Dict
import json
import os

# Create MCP server instance
mcp = FastMCP("Student Score Server")

DATA_FILE = os.path.join("data", "students.json")


def load_data() -> List[Dict]:
    """Load student data from JSON file."""
    if not os.path.exists(DATA_FILE):
        raise FileNotFoundError("Data file not found.")
    with open(DATA_FILE, "r", encoding="utf-8") as f:
        return json.load(f)


@mcp.tool()
def calculate_average(scores: List[float]) -> float:
    """
    Calculate average score from a list of scores.
    Demonstrates structured input.
    """
    if not scores:
        raise ValueError("Score list cannot be empty.")
    return sum(scores) / len(scores)


@mcp.tool()
def get_top_student() -> Dict:
    """
    Find the student with the highest total score.
    Demonstrates reading local file.
    """
    data = load_data()
    if not data:
        raise ValueError("No student data available.")

    def total_score(student):
        return student["usual_score"] + student["final_score"]

    top_student = max(data, key=total_score)
    return top_student


@mcp.tool()
def get_failing_students() -> List[Dict]:
    """
    Return students whose total score is below 60.
    Demonstrates file reading + logic.
    """
    data = load_data()
    failing = []

    for s in data:
        total = s["usual_score"] + s["final_score"]
        if total < 120:  # assuming full score 200, fail threshold = 60%
            failing.append(s)

    return failing


@mcp.tool()
def get_student_by_id(student_id: str) -> Dict:
    """
    Find a student by student ID.
    Demonstrates exception handling.
    """
    if not student_id:
        raise ValueError("Student ID cannot be empty.")

    data = load_data()

    for s in data:
        if s["student_id"] == student_id:
            return s

    raise ValueError("Student not found.")

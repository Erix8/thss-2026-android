import asyncio
from fastmcp import Client
from server import mcp


async def main():
    async with Client(mcp) as client:
        # List all tools
        tools = await client.list_tools()
        print("Available tools:", [tool.name for tool in tools])

        print("\n=== Normal Calls ===")

        # 1. calculate_average (structured input)
        result = await client.call_tool("calculate_average", {"scores": [80, 90, 100]})
        print("Average:", result.data)

        # 2. get_top_student (file read)
        result = await client.call_tool("get_top_student", {})
        print("Top student:", result.data)

        # 3. get_failing_students
        result = await client.call_tool("get_failing_students", {})
        print("Failing students:", result.data)

        # 4. get_student_by_id
        result = await client.call_tool("get_student_by_id", {"student_id": "1001"})
        print("Student 1001:", result.data)

        print("\n=== Exception Tests ===")

        # Empty list exception
        try:
            await client.call_tool("calculate_average", {"scores": []})
        except Exception as e:
            print("Empty list error:", e)

        # Invalid ID
        try:
            await client.call_tool("get_student_by_id", {"student_id": ""})
        except Exception as e:
            print("Invalid ID error:", e)

        # Non-existent ID
        try:
            await client.call_tool("get_student_by_id", {"student_id": "9999"})
        except Exception as e:
            print("Not found error:", e)


if __name__ == "__main__":
    asyncio.run(main())

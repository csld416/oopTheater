import os

def count_java_lines(directory):
    total = 0
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, 'r', encoding='utf-8') as f:
                    lines = f.readlines()
                    total += len(lines)
    return total

path = "/Users/csld/NetBeansProjects/OOP2025_threater/src"
print("Total Java lines of code:", count_java_lines(path))


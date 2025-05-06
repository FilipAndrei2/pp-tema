import os
from typing import List, Tuple, Generator


def filter_existing_files(file_paths: List[str]) -> Generator[str, None, None]:
    for file_path in file_paths:
        if os.path.isfile(file_path):
            yield file_path


def filter_text_files(file_paths: Generator[str, None, None]) -> Generator[str, None, None]:
    for file_path in file_paths:
        if os.path.isfile(file_path) and file_path.endswith(".txt"):
            yield file_path


def lines_count_and_path(file_paths: Generator[str, None, None]) -> Generator[Tuple[str, int], None, None]:
    for file_path in file_paths:
        if os.path.isfile(file_path):
            with open(file_path, "r") as file:
                line_count: int = sum(1 for _ in file)
                yield file_path, line_count


def print_filename_and_line_counts(file_and_line_counts: List[Tuple[str, int]]) -> None:
    for file_path, lines_count in file_and_line_counts:
        print(f"{file_path} : {lines_count}")


if __name__ == "__main__":
    list_of_paths: List[str] = ["test1/dir", "test1/a.txt", "test1/b.txt", "test2/dir", "test2/a", "test2/x.txt", "test3/a/a.txt", "test3/b"]
    print_filename_and_line_counts(list(lines_count_and_path(filter_text_files(filter_existing_files(list_of_paths)))))

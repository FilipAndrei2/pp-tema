from abc import ABCMeta, abstractmethod
import copy
import json
import textwrap
from typing import List


class FileFactory:
    def factory(self, file_type: str):
        if file_type == "HTML":
            return HTMLFile()
        elif file_type == "JSON":
            return JSONFile()
        elif file_type == "ARTICOL":
            return ArticleTextFile()
        elif file_type == "BLOG":
            return BlogTextFile()
        else:
            raise ValueError("Invalid data type. Only HTML, JSON, ARTICOL, BLOG are allowed.")


class File(metaclass=ABCMeta):
    __title: str = ""
    __author: str = ""
    __paragraphs: List[str] = []

    @abstractmethod
    def read_file_from_stdin(self) -> None:
        ...

    def get_title(self) -> str:
        return self.__title

    def get_author(self) -> str:
        return self.__author

    def get_paragraphs(self) -> List[str]:
        return self.__paragraphs

    def set_title(self, title: str) -> None:
        self.__title = title

    def set_author(self, author: str) -> None:
        self.__author = author

    def empty_list(self) -> None:
        self.__paragraphs = []

    def set_paragraphs(self, paragraph: str) -> None:
        self.__paragraphs.append(paragraph)


class HTMLFile(File):
    def read_file_from_stdin(self) -> None:
        self.empty_list()
        self.set_title(input("Enter file title: "))
        self.set_author(input("Enter author: "))
        number_of_paragraphs = int(input("Enter number of paragraphs (must be greater than 0): "))
        while number_of_paragraphs <= 0:
            number_of_paragraphs = int(input("Invalid number of paragraphs, must be greater than 0! Please re-enter: "))

        for i in range(0, number_of_paragraphs):
            self.set_paragraphs(input(f"Enter paragraph number {i + 1}: "))

    def __init__(self) -> None:
        self.read_file_from_stdin()

    def print_html(self) -> None:
        file_name = f"{self.get_title()}.html"
        with open(file_name, "w") as file:
            file.write(f"<!DOCTYPE html>\n<html>\n<head>\n<title>{super().get_title()}</title>\n</head>\n<body>\n")
            file.write(f"<p><b>Author : {self.get_author()}</b></p>\n<h1>Content : </h1><br>\n")
            paragraphs = self.get_paragraphs()

            for paragraph in paragraphs:
                file.write(f"<p>{paragraph}</p><br>\n")

            file.write("</body>\n</html>")

        print(f"\nFile {self.get_title()}.html was successfully created!\n")


class JSONFile(File):
    def read_file_from_stdin(self) -> None:
        self.empty_list()
        self.set_title(input("Enter file title: "))
        self.set_author(input("Enter author: "))
        number_of_paragraphs = int(input("Enter number of paragraphs (must be greater than 0): "))
        while number_of_paragraphs <= 0:
            number_of_paragraphs = int(input("Invalid number of paragraphs, must be greater than 0! Please re-enter: "))

        for i in range(0, number_of_paragraphs):
            self.set_paragraphs(input(f"Enter paragraph number {i + 1}: "))

    def __init__(self) -> None:
        self.read_file_from_stdin()

    def print_json(self) -> None:
        file_name = f"{self.get_title()}.json"
        data = {"Title": self.get_title(), "Author": self.get_author(), "Paragraphs": self.get_paragraphs()}
        with open(file_name, "w") as file:
            json.dump(data, file, indent=4)
        print(f"\nFile {self.get_title()}.json was successfully created!\n")


class TextFile(File, metaclass=ABCMeta):
    __template: str = ""

    @abstractmethod
    def print_text(self) -> None:
        ...

    def clone(self) -> 'TextFile':
        return copy.copy(self)

    def set_template(self, template: str) -> None:
        self.__template = template

    def get_template(self) -> str:
        return self.__template


class ArticleTextFile(TextFile):
    def read_file_from_stdin(self) -> None:
        self.empty_list()
        self.set_title(input("Enter file title: "))
        self.set_author(input("Enter author: "))
        number_of_paragraphs = int(input("Enter number of paragraphs (must be greater than 0): "))
        while number_of_paragraphs <= 0:
            number_of_paragraphs = int(input("Invalid number of paragraphs, must be greater than 0! Please re-enter: "))

        for i in range(0, number_of_paragraphs):
            self.set_paragraphs(input(f"Enter paragraph number {i + 1}: "))

    def __init__(self) -> None:
        self.read_file_from_stdin()
        self.set_template("Article")
        file_name = f"{self.get_title()}.txt"
        with open(file_name, "w") as file:
            file.write(f"Template : {self.get_template()}\n")
            file.write(f"Title : {self.get_title()}\n")
            file.write(f"Author : {self.get_author()}\n")
            file.write(f"Content :\n")
            paragraphs = self.get_paragraphs()
            for paragraph in paragraphs:
                file.write(f"{paragraph}\n")

    def print_text(self) -> None:
        super().print_text()

        file_name = f"{self.get_title()}.txt"
        with open(file_name, "r") as file:
            lines = file.readlines()

        with open(file_name, "w") as file:
            lines[1] = self.get_title()
            file.write(f"{lines[1].center(120)}\n")
            lines[2] = f"by {self.get_author()}"
            file.write(f"{lines[2].rjust(120)}\n")
            for i in range(4, len(lines)):
                file.write(textwrap.fill(lines[i], 120))

        print(f"\nFile of type {self.get_template()} was successfully created!\n")


class BlogTextFile(TextFile):
    def read_file_from_stdin(self) -> None:
        self.empty_list()
        self.set_title(input("Enter file title: "))
        self.set_author(input("Enter author: "))
        number_of_paragraphs = int(input("Enter number of paragraphs (must be greater than 0): "))
        while number_of_paragraphs <= 0:
            number_of_paragraphs = int(input("Invalid number of paragraphs, must be greater than 0! Please re-enter: "))

        for i in range(0, number_of_paragraphs):
            self.set_paragraphs(input(f"Enter paragraph number {i + 1}: "))

    def __init__(self) -> None:
        self.read_file_from_stdin()
        self.set_template("Blog")
        file_name = f"{self.get_title()}.txt"
        with open(file_name, "w") as file:
            file.write(f"Template : {self.get_template()}\n")
            file.write(f"Title : {self.get_title()}\n")
            file.write(f"Author : {self.get_author()}\n")
            file.write(f"Content :\n")
            paragraphs = self.get_paragraphs()
            for paragraph in paragraphs:
                file.write(f"{paragraph}\n")

    def print_text(self) -> None:
        super().print_text()

        file_name = f"{self.get_title()}.txt"
        with open(file_name, "r") as file:
            lines = file.readlines()

        with open(file_name, "w") as file:
            lines[1] = self.get_title()
            file.write(f"{textwrap.fill(lines[1], 120)}\n")
            for i in range(4, len(lines)):
                file.write(textwrap.fill(lines[i], 120))
            lines[2] = f"\nWritten by {self.get_author()}"
            file.write(f"\n{textwrap.fill(lines[2], 120)}")

        print(f"\nFile of type {self.get_template()} was successfully created!\n")


if __name__ == "__main__":
    factory = FileFactory()

    html_file = factory.factory("HTML")
    html_file.print_html()

    json_file = factory.factory("JSON")
    json_file.print_json()

    article_file = factory.factory("ARTICOL")
    article_file.print_text()

    blog_file = factory.factory("BLOG")
    blog_file.print_text()

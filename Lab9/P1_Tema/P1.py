import subprocess
import os
import tempfile
from abc import ABCMeta, abstractmethod




class Handler(metaclass=ABCMeta):
   @abstractmethod
   def set_next(self, handler: 'Handler') -> 'Handler':
       pass


   @abstractmethod
   def handle(self, content: str) -> None:
       pass




class AbstractHandlerAndCommand(Handler):
   _next_handler: 'Handler' = None


   def set_next(self, handler: 'Handler') -> 'Handler':
       self._next_handler = handler
       return handler


   def handle(self, content: str) -> None:
       handled = self.execute(content)


       if not handled:
           if self._next_handler:
               self._next_handler.handle(content)
           else:
               print("Fisierul nu este recunoscut.")


   @abstractmethod
   def execute(self, content: str) -> bool:
       pass




class KotlinHandler(AbstractHandlerAndCommand):
   def execute(self, content: str) -> bool:
       if "fun main" in file_content:
           print("Fisierul este Kotlin!")
           with tempfile.NamedTemporaryFile(suffix=".kt", delete=False) as temp:
               temp.write(file_content.encode())
               temp.close()
               subprocess.check_call(["kotlinc", temp.name, "-include-runtime", "-d", f"{temp.name}.jar"])
               print(subprocess.check_output(["java", "-jar", f"{temp.name}.jar"]))
               os.unlink(temp.name)
               os.unlink(f"{temp.name}.jar")
           return True
       return False




class PythonHandler(AbstractHandlerAndCommand):
   def execute(self, content: str) -> bool:
       if "def " in file_content or "__name__" in file_content:
           print("Fisierul este Python!")
           print(subprocess.check_output(["python3", "-c", file_content]))
           return True
       return False




class BashHandler(AbstractHandlerAndCommand):
   def execute(self, content: str) -> bool:
       if file_content.startswith("#!"):
           print("Fisierul este Bash!")
           print(subprocess.check_output(["bash", "-c", file_content]))
           return True
       return False




class JavaHandler(AbstractHandlerAndCommand):
   def execute(self, content: str) -> bool:
       if "public static void main" in file_content:
           print("Fisierul este Java!")
           with tempfile.NamedTemporaryFile(suffix=".java", delete=False) as temp:
               os.rename(temp.name, "/tmp/Main.java")
               temp.name = temp.name.replace(temp.name, "/tmp/Main.java")
               temp.write(file_content.encode())
               temp.close()
               subprocess.check_call(["javac", temp.name])
               class_file = temp.name.replace(".java", "")
               print(subprocess.check_output(["java", "-cp", os.path.dirname(temp.name), os.path.basename(class_file)]))
               os.unlink(temp.name)
               os.unlink(class_file + ".class")
           return True
       return False




if __name__ == "__main__":
   python_handler: PythonHandler = PythonHandler()
   kotlin_handler: KotlinHandler = KotlinHandler()
   java_handler: JavaHandler = JavaHandler()
   bash_handler: BashHandler = BashHandler()


   python_handler.set_next(kotlin_handler).set_next(java_handler).set_next(bash_handler)


   with open(input("Dati numele fisierului : "), "r") as file:
       file_content: str = file.read()


   python_handler.handle(file_content)


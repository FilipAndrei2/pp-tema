from threading import Thread
import queue
import time


class Consumator(Thread):
    def __init__(self, Queue: queue.Queue) -> None:
        Thread.__init__(self)
        self.queue: queue.Queue = Queue

    def run(self) -> None:
        while True:
            element: int = self.queue.get()
            if element is None:
                break
            print("mesaj de la consumator : am utlizat un element")
            print(f"mesaj de la consumator : mai am disponibil {self.queue.qsize()} elemente")
            self.queue.task_done()
            time.sleep(1)


class Producator(Thread):
    def __init__(self, Queue: queue.Queue) -> None:
        Thread.__init__(self)
        self.queue: queue.Queue = Queue

    def run(self):
        for i in range(10):
            self.queue.put(1)
            print(f"mesaj de la producator : am produs {self.queue.qsize()} elemente")
            time.sleep(1)
        self.queue.put(None)


if __name__ == "__main__":
    q: queue.Queue = queue.Queue()
    producator: Producator = Producator(q)
    consumator: Consumator = Consumator(q)
    producator.start()
    consumator.start()
    producator.join()
    consumator.join()

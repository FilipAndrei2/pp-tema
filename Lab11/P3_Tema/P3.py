from threading import Thread, Lock
from queue import Queue
from typing import List, Callable, Tuple, Dict, Any, Iterable


class ThreadPool:
    def __init__(self, num_threads: int) -> None:
        self.tasks: Queue = Queue()
        self.threads: List[Thread] = []
        self.lock: Lock = Lock()
        for _ in range(num_threads):
            thread: Thread = Thread(target=self._worker)
            thread.start()
            self.threads.append(thread)

    def _worker(self) -> None:
        while True:
            func: Callable[..., ...]
            args: Tuple[Any, ...]
            kwargs: Dict[str, Any]
            func, args, kwargs = self.tasks.get()
            if func is None:
                break
            with self.lock:
                func(*args, **kwargs)
            self.tasks.task_done()

    def map(self, func: Callable[..., ...], iterable: Iterable) -> None:
        for item in iterable:
            self.tasks.put((func, (item,), {}))

    def join(self) -> None:
        self.tasks.join()

    def terminate(self) -> None:
        for _ in self.threads:
            self.tasks.put((None, (), {}))
        for thread in self.threads:
            thread.join()

    def __enter__(self) -> 'ThreadPool':
        return self

    def __exit__(self, exc_type: Any, exc_val: Any, exc_tb: Any) -> None:
        self.terminate()


if __name__ == "__main__":
    with ThreadPool(6) as pool:
        pool.map(print, range(20))
        pool.join()

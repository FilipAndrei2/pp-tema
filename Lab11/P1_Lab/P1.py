import threading
import multiprocessing
from concurrent.futures import ThreadPoolExecutor
from typing import List, Callable, Tuple
import time
import random
from sympy import isprime

n_of_threads: int = 2
size: int = 10
v: List[int] = [random.randint(1, 100) for _ in range(size)]
z: List[int] = [0 for _ in range(size)]


def countdown(numbers: List[int]) -> List[int]:
    sorted_numbers: List[int] = sorted(numbers)
    primes: List[int] = [num for num in sorted_numbers if isprime(num)]
    squared_primes: List[int] = [num ** 2 for num in primes]
    return squared_primes


def increment(numbers: List[int]) -> List[int]:
    return [num + 1 for num in numbers]


def operation_gil(numbers: List[int], function: Callable[[List[int]], List[int]], number_of_threads: int) -> List[int]:
    threads: List[threading.Thread] = []
    results: List[int] = []
    segment_size: int = len(numbers) // number_of_threads
    for i in range(number_of_threads):
        start1: int = i * segment_size
        end1: int = (i + 1) * segment_size if i < number_of_threads - 1 else len(numbers)
        segment: List[int] = numbers[start1:end1]
        thread: threading.Thread = threading.Thread(target=lambda x, y: results.extend(function(y)), args=(i, segment))
        threads.append(thread)
        thread.start()
    for thread in threads:
        thread.join()
    return sorted(results)


def operation_multiprocessing(numbers: List[int], function: Callable[[List[int]], List[int]], number_of_processes: int) -> List[int]:
    with multiprocessing.Pool(processes=number_of_processes) as pool:
        segment_size: int = len(numbers) // number_of_processes
        segments: List[List[int]] = [numbers[i * segment_size:(i + 1) * segment_size] if i < number_of_processes - 1 else numbers[i * segment_size:] for i in range(number_of_processes)]
        results: List[List[int]] = pool.map(function, segments)
    return [item for sublist in results for item in sublist]


def ver_1(numbers: List[int], zeros: List[int]) -> Tuple[List[int], List[int]]:
    return operation_gil(numbers, countdown, n_of_threads), operation_gil(zeros, increment, n_of_threads)


def ver_2(numbers: List[int], zeros: List[int]) -> Tuple[List[int], List[int]]:
    countdown(numbers)
    increment(zeros)
    return countdown(numbers), increment(zeros)


def ver_3(numbers: List[int], zeros: List[int]) -> Tuple[List[int], List[int]]:
    return operation_multiprocessing(numbers, countdown, n_of_threads // 2), operation_multiprocessing(zeros, increment, n_of_threads // 2)


def ver_4(numbers: List[int], zeros: List[int]) -> Tuple[List[int], List[int]]:
    with ThreadPoolExecutor(max_workers=n_of_threads // 2) as executor:
        countdown_future = executor.submit(countdown, numbers)
        increment_future = executor.submit(increment, zeros)

    countdown_result = countdown_future.result()
    increment_result = increment_future.result()

    return countdown_result, increment_result


if __name__ == '__main__':
    vect: List[int]
    zers: List[int]
    print(f"Vector de numere initial : {v}")

    start: float = time.time()
    vect, zers = ver_1(v, z)
    end: float = time.time()
    print("\nTimp executie pseudoparalelism cu GIL")
    print(end - start)
    print(f"Vector de numere : {vect}")

    start: float = time.time()
    vect, zers = ver_2(v, zers)
    end: float = time.time()
    print("\nTimp executie secvential")
    print(end - start)
    print(f"Vector de numere : {vect}")

    start: float = (time.time())
    vect, zers = ver_3(v, zers)
    end: float = time.time()
    print("\nTimp executie paralela cu multiprocessing")
    print(end - start)
    print(f"Vector de numere : {vect}")

    start: float = time.time()
    vect, zers = ver_4(v, zers)
    end: float = time.time()
    print("\nTimp executie paralela cu concurrent.futures")
    print(end - start)
    print(f"Vector de numere : {vect}")

    print(f"\nVector de incrementari : {zers}")

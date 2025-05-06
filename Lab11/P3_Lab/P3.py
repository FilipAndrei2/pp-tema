import multiprocessing
from typing import List


def multiply_by_alpha(v: List[int], alpha: int) -> List[int]:
    return [alpha * element for element in v]


def sort_data(v: List[int]) -> List[int]:
    return sorted(v)


def print_data(v: List[int]) -> None:
    print(f"Vectorul dupa inmultire si ordonare : {v}")


class ProcesTest(multiprocessing.Process):
    def run(self) -> None:
        print(f'am apelat metoda run() in procesul: {self.name}')
        return


if __name__ == '__main__':
    a: int = 2
    vect: List[int] = [1, 5, 4, 3, 2]
    print(f"Vectorul inainte de inmultire si ordonare : {vect}")

    with multiprocessing.Pool() as pool:
        vect = pool.apply(multiply_by_alpha, args=(vect, a))
        vect = pool.apply(sort_data, args=(vect,))
        pool.apply(print_data, args=(vect,))


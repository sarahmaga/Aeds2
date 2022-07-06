from datetime import datetime as date
from movie import Movie
from collections import deque
path = "filmes/"

def main():
    now = date.now()
    stack = deque()
    keyboard = input()
    while keyboard != "FIM":
        atual = Movie(path+keyboard)
        stack.append(atual)
        keyboard = input()
    for i in stack:   
        print(i)
    now2 = date.now()
    time = now2 - now
    print("Algorithm speed", time.microseconds/1000000, "seconds")

if __name__ == "__main__":
    main()

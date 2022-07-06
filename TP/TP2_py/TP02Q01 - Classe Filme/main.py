from datetime import datetime as date
from movie import Movie
path = "filmes/"

def main():
    now = date.now()
    keyboard = input()
    while keyboard != "FIM":
        atual = Movie(path+keyboard)
        print(atual)
        keyboard = input()
    now2 = date.now()
    time = now2 - now
    print("Algorithm speed", time.microseconds/1000000, "seconds")

if __name__ == "__main__":
    main()

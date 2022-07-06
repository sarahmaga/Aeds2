from movie import Movie

def sequencial_search(deque, key):
    resp = False
    for i in deque:
        if Movie.get_name(i) == key:
            resp = True
            break
    return resp
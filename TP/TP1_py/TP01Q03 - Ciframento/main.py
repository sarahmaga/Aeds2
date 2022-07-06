ENCRIPTAR = 1
DECRIPTAR = 0

def cifra (s, chave,mode) -> str:
    cifrada = ""
    if mode == ENCRIPTAR:
        for i in s:
            c = chr(ord(i)+chave) # ord returns ascii number, then chr return the new character
            cifrada += c
    elif mode == DECRIPTAR:
        for i in s:
            c = chr(ord(i) - chave)
            cifrada += c

    return cifrada

def main():
    entrada = input("Entrada:")
    chave = 1
    try:
        while entrada:
            resp = cifra (entrada,chave,ENCRIPTAR)
            print("Encriptada:",resp)
            resp = cifra (resp,chave,DECRIPTAR)
            print("Decriptada:",resp)
            entrada = input("Entrada:")
    except:
        pass
if __name__ == "__main__":
    main()
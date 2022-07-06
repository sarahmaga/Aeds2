import math
import random


def alt_aleatoria(s,chave) -> str:
    resp = ""
    #random.seed(chave)
    #r1 = math.ceil(random.random())
    
    for i in s:
        r2 = random.randint(97,122)
        c = chr(r2)
        resp +=c

    return resp


def main():
    try:
        entrada = input()
        while entrada:
            print(entrada + "-->" + alt_aleatoria(entrada,2))
            entrada = input()
    except:
        pass



if __name__ == "__main__":
   
    main()
    
    
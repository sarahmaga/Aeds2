def palindromo (entrada:str):
    resp:bool
    j = len(entrada) - 1

    for i in entrada:
        if i == entrada[j]:
            resp =True
            j -=1
        else:
            resp = False
            break

    return resp

def main():
    try : 
        entrada = input()
        while entrada:
            if palindromo(entrada) == True:
                print("SIM")
            else:
                print ("NAO")
            entrada = input()
    except :
        pass

if __name__ == "__main__":
    main()
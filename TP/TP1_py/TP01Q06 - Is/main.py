def isJustConso(s: str):
    s.lower()
    resp = True

    if 'a' in s or 'e' in s or 'i' in s or 'o' in s or 'u' in s:
        resp = False

    for i in s:
        if (i >= chr(48) and i <= chr(57) or i == '.' or i == ',') == True:
            resp = False

    return resp


def isJustVogal(s: str):
    s.lower()
    resp = True
    # if ('a' not in s and 'e' not in s and 'i' not in s and 'o' not in s and 'u' not in s):
    for i in s:
        if (i == 'a' or i == 'e' or i == 'i' or i == 'o' or i == 'u') == False:
            resp = False
            break

    return resp


def isReal(s):
    resp = True
    p = 0
    m = 0
    c = 0
    for i in s:
        if (i >= chr(48) and i <= chr(57) or i == '.' or i == ',' or s[0] == '-') == False:
            resp = False
        if i >= chr(48) and i <= chr(57):  # Conta numeros para verificar se o mesmo possui numeros
            c += 1
        if i == '.' or i == ',':  # Conta pontos e virgulas
            p += 1
        if i == '-':  # Conta sinais de menos
            m += 1
    if p > 1 or m > 1 or c == 0: # Numeros com nenhum numero, mais de 1 virgula e pontos
        resp = False

    return resp


def isInt(s):
    resp = True
    m = 0
    for i in s:
        if i >= chr(48) and i <= chr(57) or s[0] == '-':
            resp = True
            if i == '-':
                m += 1
        else:
            resp = False
            break
    if m > 1:
        resp = False

    return resp


def main():
    print("Exercicio ", end='')
    print("de IF")
    entrada = input()

    while entrada:
        print(entrada, "Possui somente consoantes") if isJustConso(entrada) else print(entrada, "Nao possui somente consoantes")
        print(entrada, "Possui somente vogais") if isJustVogal(entrada) else print(entrada, "Nao possui somente vogais")
        print(entrada, "E um numero real") if isReal(entrada) else print(entrada, "Nao e um numero real")
        print(entrada, "E um numero inteiro") if isInt(entrada) else print(entrada, "Nao e um numero inteiro")
        entrada = input()


if __name__ == "__main__":
    main()

def readArchive(filename): # returns the whole file as a string
    result:str = open(filename)
    result:str = result.read()
    return result

def main():
    filename = "data.txt"
    print(readArchive(filename))

if __name__ == "__main__":
    main()
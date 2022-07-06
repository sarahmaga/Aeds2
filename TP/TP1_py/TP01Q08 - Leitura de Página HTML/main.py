from func import readHTML
import func

def main():
    keyboard = input()
    page = readHTML(keyboard)
    print(page)
    #print(func.findTitle(page))
    #print(func.removeTags(page))

if __name__ == "__main__":
    main()
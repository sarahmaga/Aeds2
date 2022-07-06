from urllib.request import urlopen

def readHTML(url:str): # Returns a string with the html elements
    pageText = ""
    page = urlopen(url)
    html_bytes = page.read()
    pageText = html_bytes.decode("utf-8")
    return pageText

def removeTags(html_text):
    result = ""
    i = 0
    while i <= len(html_text) - 1:
        if html_text[i] == '<':
            while html_text[i] != '>':
                i+=1
        else:
            result += html_text[i]
        i+=1
    return result

def findTitle(html_text):
    result = ""
    tag = html_text.find("<title>") # Find first index of this string
    first_index = tag + len("<title>") # Gets the final index of the last character of this string
    last_index = html_text.find("</title>") # Find first index of this string
    result = html_text[first_index:last_index] # Gets the string between these indexes

    return result
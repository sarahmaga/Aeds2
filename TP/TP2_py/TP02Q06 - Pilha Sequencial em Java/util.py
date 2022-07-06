def removeTags(html_text):
    result = ""
    i = 0
    while i <= len(html_text) - 1:
        if html_text[i] == '<':
            while html_text[i] != '>':
                i += 1
        else:
            result += html_text[i]
        i += 1
    return result


def removeParentheses(html_text):
    result = ""
    first_index = html_text.find("(")
    last_index = html_text.find(")")
    result = html_text[first_index:last_index + 1]
    return result


def convertToMinutes(html_text):
    result = html_text.replace("h", "")
    result = result.replace("m", "")
    if len(result) == 4:
        h: int = int(result[0])
        h = h * 60
        m: int = int(result[2:4])
        result = h + m
    elif len(result) == 3:
        h: int = int(result[0])
        h = h * 60
        m: int = int(result[2])
        result = h + m
    elif len(result) == 2:
        m: int = int(result[0:2])
        result = m
    else:
        result = int(result)
    return result

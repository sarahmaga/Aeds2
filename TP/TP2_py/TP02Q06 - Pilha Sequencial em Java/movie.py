import util
from datetime import datetime as date
class Movie:
    def readFile(self,filename):
        html_text: str = open(filename)
        html_text: str = html_text.read()

        # Find title name
        first_index = html_text.find("<h2")
        last_index = html_text.find("</h2>")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        parentheses = util.removeParentheses(result)
        result = result.replace(parentheses, "")
        result = result.replace("amp;", "")
        result =  result.strip()
        self.name:str = result  
        
        # Find runtime
        first_index = html_text.find("<span class=\"runtime\">")
        last_index = html_text.find("<ul class=\"auto actions\">")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        result = result.strip()
        result = util.convertToMinutes(result)
        self.runtime:int = result

        # Find original_title
        if "<p class=\"wrap\"><strong>Título original</strong>" in html_text:
            tag = html_text.find("<p class=\"wrap\"><strong>Título original</strong>")
            first_index = tag + len("<p class=\"wrap\"><strong>Título original</strong>")
            last_index = html_text.find("<strong><bdi>Situação")
            result = html_text[first_index:last_index]
            result = util.removeTags(result)
            result =  result.strip()
            self.original_title:str = result
        else:
            self.original_title :str= self.name
        
        # Find release date
        first_index = html_text.find("<span class=\"release\">")
        last_index = html_text.find("<span class=\"genres\">")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        parentheses = util.removeParentheses(result)
        result = result.replace(parentheses, "")
        result =  result.strip()
        result = date(int(result[6:10]), int(result[3:5]), int(result[0:2]))
        self.release_date:date = result
        
        # Find genres
        first_index = html_text.find("<span class=\"genres\">")
        last_index = html_text.find("<span class=\"runtime\">")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        result = result.replace("&nbsp;", "") 
        result = result.strip()
        self.genres:str = result

        # Find original language
        tag = html_text.find("<p><strong><bdi>Idioma original</bdi></strong>")
        first_index = tag + len("<p><strong><bdi>Idioma original</bdi></strong>")
        last_index = html_text.find("<p><strong><bdi>Orçamento</bdi></strong>")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        result = result.strip()
        self.original_language:str = result
        
        # Find situation
        tag = html_text.find("<strong><bdi>Situação</bdi></strong>")
        first_index = tag + len("<strong><bdi>Situação</bdi></strong>")
        last_index = html_text.find("<p><strong><bdi>Idioma original</bdi></strong>")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        result = result.strip()
        self.situacion:str = result

        # Find budget 
        tag = html_text.find("p><strong><bdi>Orçamento</bdi></strong>")
        first_index = tag + len("p><strong><bdi>Orçamento</bdi></strong>")
        last_index = html_text.find("<p><strong><bdi>Receita</bdi></strong>")
        result = html_text[first_index:last_index]
        result = util.removeTags(result)
        result = result.replace("$", "")
        result = result.replace(",", "")
        result = result.strip()
        if "-" not in result:
            result = float(result)
        else:
            result = 0.0
        self.budget:float = result
        

        # Find keywords
        tag = html_text.find("<h4><bdi>Palavras-chave</bdi></h4>")
        first_index = tag + len("<h4><bdi>Palavras-chave</bdi></h4>")
        last_index = html_text.find("<h4 dir=\"auto\">Avaliação do conteúdo</h4>")
        result = html_text[first_index:last_index]
        vector = []
        for i in result.split("<"):
            if  "href=" in i :
               vector.append(i)
        i = 0
        while i < len(vector):
            vector[i] = "<" + vector[i]
            vector[i] =  util.removeTags(vector[i])
            i = i + 1
            
        result = vector
        self.keywords = result

    # To String
    def __str__(self) -> str:
        result = ""
        result = self.name + " " + self.original_title + " " + self.release_date.strftime("%d/%m/%Y") \
            + " " + str(self.runtime) + " " + self.genres + " " + self.original_language + " " \
                + self.situacion + " "
        result += format(self.budget, '.1E') + " "
        result += str(self.keywords)
        
        return result

    # prints a movie attributes
    def print_movie(self):
        print(self.name,"", end='')
        print(self.original_title,"", end='')
        print(self.release_date.strftime("%d/%m/%Y"),"", end='')
        print(self.runtime,"", end = '')
        print (self.genres,end = '')
        print(', ', end = '')
        print(self.original_language, end = '')
        print(', ', end = '')
        print(self.situacion, end = '')
        print(', ', end = '')
        print (format(self.budget, '.1E'), end='')
        if  len(self.keywords) > 0:  
           print(', ', end = '')
        print(self.keywords)
    
    def __init__(self, filename):
        self.readFile(filename)


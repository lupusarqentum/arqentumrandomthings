import requests

def first_layer_document_parsing(document: str) -> list:
	problems = []
	for i in range(len(document) - 33):
		pattern1 = '<table class="problemfootertable">'
		flag1 = False
		for j in range(len(pattern1)):
			if document[i + j] != pattern1[j]:
				break
		else:
			# pattern found
			flag1 = True
		if not flag1:
			continue
		for j in range(i - 1, 6, -1):
			pattern2 = ">elbat/<"
			flag2 = True
			for k in range(len(pattern2)):
				if document[j - k] != pattern2[k]:
					flag2 = False
					break
			if flag2:
				problems.append(document[j + 1:i])
				break
	return problems

def find_math_tags(document: str) -> list:
	problems = []
	for i in range(len(document) - 2):
		if not (document[i + 2] == ">" and document[i + 1] == "-" and document[i] == "-"):
			continue
		for j in range(i - 1, -1, -1):
			if document[j - 3] + document[j - 2] + document[j - 1] + document[j] == "<!--":
				problems.append(document[j - 3: i + 3])
				break
	return problems

def get_replace_img_pairs(problem: str) -> str:
	pairs = []
	i = 0
	inimg = False
	inalt = False
	altsstr = ""
	begin = inaltbeg = -1
	while i < len(problem):
		try:
			if problem[i] + problem[i + 1] + problem[i + 2] + problem[i + 3] == "<IMG":
				inimg = True
				begin = i
		except IndexError:
			pass
		try:
			if inimg and problem[i] + problem[i + 1] + problem[i + 2] + problem[i + 3] == "T=\"$":
				inalt = True
				inaltbeg = i + 4
		except IndexError:
			pass
		try:
			if inimg and inalt and problem[i] + problem[i + 1] == "$\"":
				altsstr = problem[inaltbeg:i]
				inalt = False
		except IndexError:
			pass
		if inimg and problem[i] == ">":
			pairs.append((problem[begin: i + 1], altsstr))
			begin = inaltbeg = -1
			altsstr = ""
			inimg = False
		i += 1
	return pairs

def parse_fractions(problem: str) -> str:
	cond = True
	while cond:
		res = rem_frac(problem)
		cond = res[0]
		problem = res[1]
	return problem

def rem_frac(problem: str) -> tuple:
	p = None
	stage = 0
	ind = 0
	for i in range(len(problem)):
		if i + 3 < len(problem) and problem[i] + problem[i + 1] + problem[i + 2] + problem[i + 3] == "frac":
			stage = 1
		if stage in (1, 2) and problem[i] == "{":
			ind = i + 1
		if stage == 1 and problem[i] == "}":
			stage = 2
			p = problem[ind:i]
			continue
		if stage == 2 and problem[i] == "}":
			p1 = str(p)
			p2 = problem[ind:i]
			return (True, problem.replace("frac{" + p1 + "}{" + p2 + "}", "(" + p1 + ")/(" + p2 + ")"))
	return (False, problem)

def remove_math_tags(problem: str) -> str:
	segments_to_remove = find_math_tags(problem)
	segments_to_remove.sort(key = lambda x: len(x), reverse = True)
	for seg in segments_to_remove:
		problem = problem.replace(seg, "")
	return problem
	
def remove_img_tags(problem: str) -> str:
	replace_pairs = get_replace_img_pairs(problem)
	for p in replace_pairs:
		problem = problem.replace(p[0], p[1])
	return problem

def remove_unnecesary_info(problem: str) -> str:
	words_to_remove = ["<p/>", "<p>", "</p>", "<P>", "<I>", "</I>", "<BR>", "<br>", "<i>", "</i>", "</P>", "<P/>", "</sub>", "</SUB>", "<sub/>", "<SUB/>",
					   '<div class="catalogueproblemauthorold">', "</DIV>", "</div>", "<DIV/>", "<div/>", "</A>", "</a>", "<A/>", "<a/>"]
	for word in words_to_remove:
		problem = problem.replace(word, "")
	return problem

def remove_unnecesary_info2(problem: str) -> str:
	words_to_remove = ["/", "\\", "&", "$", "#", """<a href="view_by_author.php?author=""", "\">"]
	for word in words_to_remove:
		problem = problem.replace(word, "")
	return problem

def problem_words_replace(problem:str) -> str:
	words_to_replace = []
	#words_to_replace.append(["", ""])
	words_to_replace.append(["<SUP><TT>o</TT></SUP>", " градусов "])
	words_to_replace.append(["&alpha;", "alpha"])
	words_to_replace.append(["&beta;", "beta"])
	words_to_replace.append(["&gamma;", "gamma"])
	words_to_replace.append(["&delta;", "delta"])
	words_to_replace.append(["&alpha", "alpha"])
	words_to_replace.append(["&beta", "beta"])
	words_to_replace.append(["&gamma", "gamma"])
	words_to_replace.append(["&delta", "delta"])
	words_to_replace.append(["<sub>", "_"])
	words_to_replace.append(["<SUB>", "_"])
	words_to_replace.append(["\angle>", " угол "])
	words_to_replace.append(["angle>", " угол "])
	words_to_replace.append(["\angle", " угол "])
	words_to_replace.append(["angle", " угол "])
	words_to_replace.append(["\alpha", "alpha"])
	words_to_replace.append(["alpha", "alpha"])
	words_to_replace.append(["\beta", "beta"])
	words_to_replace.append(["beta", "beta"])
	words_to_replace.append(["\gamma", "gamma"])
	words_to_replace.append(["gamma", "gamma"])
	words_to_replace.append(["\delta", "delta"])
	words_to_replace.append(["delta", "delta"])
	words_to_replace.append(["&lt;", " < "])
	words_to_replace.append(["&gt;", " > "])
	words_to_replace.append(["&nbsp;", " "])
	words_to_replace.append(["&#151;", " - "])
	words_to_replace.append(["&#8211;", " - "])
	words_to_replace.append(["&#8212;", " - "])
	words_to_replace.append(["\neq>", " != "])
	words_to_replace.append(["\neq", " != "])
	words_to_replace.append(["neq>", " != "])
	words_to_replace.append(["neq", " != "])
	words_to_replace.append(["&deg;", " градусов "])
	words_to_replace.append(["&ndash;", " - "])
	words_to_replace.append(["&#8801;", " сравнимо по модулю с "])
	words_to_replace.append(["&times;", " на "])
	words_to_replace.append(["&sup2;", "^2 "])
	words_to_replace.append(["&sup3;", "^3 "])
	words_to_replace.append(["<sup>", "^("])
	words_to_replace.append(["<SUP>", "^("])
	words_to_replace.append(["</SUP>", ")"])
	words_to_replace.append(["<SUP/>", ")"])
	words_to_replace.append(["</sup>", ")"])
	words_to_replace.append(["<sup/>", ")"])
	words_to_replace.append(["&le;", " <= "])
	words_to_replace.append(["&ge;", " >= "])
	words_to_replace.append(["&ne;", " != "])
	words_to_replace.append(["\cdot", " * "])
	words_to_replace.append(["\ldots", " ... "])
	for word in words_to_replace:
		problem = problem.replace(word[0], word[1])
	return problem

def verify_problem_parsed(problem: str) -> str:
	if "img" in problem:
		return ""
	return problem

def fix_white_spaces(problem: str) -> str:
	problem = problem.replace(" ,", ",")
	problem = problem.replace(" ;", ";")
	return " ".join(problem.split())

def get_problems_from_document(document: str) -> list:
	problems = first_layer_document_parsing(document)
	problems = [remove_unnecesary_info(problem) for problem in problems]
	problems = [problem_words_replace(problem) for problem in problems]
	problems = [remove_math_tags(problem) for problem in problems]
	problems = [remove_img_tags(problem) for problem in problems]
	problems = [remove_unnecesary_info2(problem) for problem in problems]
	problems = [parse_fractions(problem) for problem in problems]
	problems = [verify_problem_parsed(problem) for problem in problems]
	problems = [fix_white_spaces(problem) for problem in problems]
	
	np = []
	for prop in problems:
		if len(prop) > 2:
			np.append(prop)
	
	return np

def get_document(topicid, difmin, difmax, grmin, grmax, amount) -> str:
	url = 'https://problems.ru/view_by_subject_new.php?parent=' + str(topicid) + '&start=0'
	myobj = {'difficulty_min': difmin, 'difficulty_max': difmax, 'grade_min': grmin, 'grade_max': grmax, 'viewing_params[view_amount]': amount}
	x = requests.post(url, data = myobj)
	return x.text
	
def accept_user_input() -> list:
	topicid = int(input("Введите идентификатор раздела (откройте нужный раздел или подраздел на problems.ru, числовой идентификатор будет написан в ссылке на эту страницу после ?parent=): "))
	difmin = int(input("Введите минимальную сложность задач: "))
	difmax = int(input("Введите максимальную сложность задач: "))
	grmin = int(input("Введите минимальный класс задач: "))
	grmax = int(input("Введите максимальный класс задач: "))
	amount = int(input("Введите количество задач: "))
	print("Отправка запроса на получение задач...")
	doc = get_document(topicid, difmin, difmax, grmin, grmax, amount)
	print("Ответ получен! Парсинг...")
	return get_problems_from_document(doc)

pc_ = 0
problem_blocks = []
while True:
	s = input("Добавить ещё задач? (y/n): ")
	if s != "y":
		break
	problem_blocks.append(accept_user_input())
	pc_ += len(problem_blocks[-1])
	print(f"Добавлено {len(problem_blocks[-1])} задач, всего {pc_} задач")

print(f"Всего {pc_} задач")
print("Построение текста конфиг-файла...")
problems_a = []
for block in problem_blocks:
	for ppp in block:
		ppp2 = ppp.replace("\"", "\\\"")
		problems_a.append('"' + ppp2 + '"')
inst = ", ".join(problems_a)
ft0 = """#list of problem statements, e.g. ["2+2=?", "there were two goats. how many?"]
problems = ["""
ft1 = """]

"""
ft = ft0 + inst + ft1

ft = ft.replace("Ё", "\\u0401")
ft = ft.replace("ё", "\\u0451")
cyrillic = "".join([chr(ord('А') + i) for i in range(0, 64)])
for letter_ in cyrillic:
	repl = "\\u0" + hex(1040 + ord(letter_) - ord('А'))[2:]
	ft = ft.replace(letter_, repl)

print("Текст построен! Сохранение файла...")
f = open("arqentumrandomthings-problemset.toml", 'w')
f.write(ft)
f.close()
print("Файл сохранён!")
print("Press any key to continue...")
input()
file_cnt = 0;
while(file_cnt < 2411):
    file_cnt_str = str(file_cnt)
    f = open('/Users/crayon/Desktop/代码注释一致性/论文/过时注释检测Automatic Detection of Outdated Comments During Code Changes/new_data_set/jedit/step2/chenged/' + file_cnt_str + '.java')  # 返回一个文件对象
    w = open('/Users/crayon/Desktop/代码注释一致性/论文/过时注释检测Automatic Detection of Outdated Comments During Code Changes/new_data_set/jedit/step3/changed/' + file_cnt_str + '.java','w')
    file_cnt = file_cnt + 1;
    line = f.readline()  # 调用文件的 readline()方法
    str1 = ""
    str2 = ""
    cnt = 0
    while line:
     # print(line)                 # 后面跟 ',' 将忽略换行符
        if(cnt == 0):
            str1 = line
        if(cnt != 0):
            str2 = str2 + line
        cnt = cnt + 1
        line = f.readline()
    f.close()
    if(cnt != 1):
        print('原始注释语句：  ' + str1)
        print('原始代码语句：   ' + str2) #原始行
    # ————————————————————————————————————————————————————————  原始行
        import re
        compileX = re.compile("\W")
        rX1 = compileX.findall(str1)
        rX2 = compileX.findall(str2)
    # print(rX2)
    # print(rX1)
        for char in str1:
            if(char in rX1):
                str1 = str1.replace(char, ' ')
        for char in str2:
            if(char in rX2):
                str2 = str2.replace(char, ' ')
        print('去除非单词后的注释语句：   ' + str1)
        print('去除非单词后的代码语句；   ' + str2)  #去除非字母词
        print()
    # ————————————————————————————————————————————————————————  去除非字母词

        import nltk
    # brown.categories()
    # print(len(brown.sents()))
    # print(len(brown.words()))
        tokens1=nltk.word_tokenize(str1)
        tokens2=nltk.word_tokenize(str2)

        print('分词转换后的注释：  ')
        print(tokens1)
        print('分词转换后的代码：  ')
        print(tokens2)
        print()
    # ————————————————————————————————————————————————————————  分词

        from nltk.stem.porter import PorterStemmer
        from nltk.stem.lancaster import LancasterStemmer
        from nltk.stem.snowball import SnowballStemmer

        #比较不同的词干提取方法
        stemmers = ['PORTER', 'LANCASTER', 'SNOWBALL']
        stemmer_porter = PorterStemmer()
        stemmer_lancaster = LancasterStemmer()
        stemmer_snowball = SnowballStemmer('english')
        # print(tokens1)

        formatted_row = '{:>16}' * (len(stemmers) + 1)
        # print ('\n', formatted_row.format('WORD', *stemmers), '\n')
        words1 = tokens1
        words2 = tokens2
        cnt = 0
        for word in tokens1:
            stemmed_words = [stemmer_porter.stem(word),
                    stemmer_lancaster.stem(word), stemmer_snowball.stem(word)]
            tokens1[cnt] = stemmer_snowball.stem(word)  #转换成词干
            cnt = cnt + 1

        cnt = 0
        for word in tokens2:
            stemmed_words = [stemmer_porter.stem(word),
                    stemmer_lancaster.stem(word), stemmer_snowball.stem(word)]
            tokens2[cnt] = stemmer_snowball.stem(word)  #转换成词干
            cnt = cnt + 1
            # print (formatted_row.format(word, *stemmed_words))
        print('词干提取后的注释：  ')
        print(tokens1)
        print('词干提取后的代码：  ')
        print(tokens2)
        print()
        # ————————————————————————————————————————————————————————  词干提取  words1/2为原来的，tokens1/2为词干


        from nltk.stem import WordNetLemmatizer

        #
        # # 对比不同词形的还原器
        # lemmatizers = ['NOUN LEMMATIZER', 'VERB LEMMATIZER']
        # lemmatizer_wordnet = WordNetLemmatizer()
        #
        # formatted_row = '{:>24}' * (len(lemmatizers) + 1)
        # print('\n', formatted_row.format('WORD', *lemmatizers), '\n')
        # for word in tokens1:
        #     lemmatized_words = [lemmatizer_wordnet.lemmatize(word, pos='n'),
        #            lemmatizer_wordnet.lemmatize(word, pos='v')]
        #     print (formatted_row.format(word, *lemmatized_words))

        # ————————————————————————————————————————————————————————  词性还原，还原单词的基本形式  不需要 words1/2为原来的，tokens1/2为词干
        from nltk.corpus import stopwords
        tokens1 = [word for word in tokens1 if word not in stopwords.words('english')]
        tokens2 = [word for word in tokens2 if word not in stopwords.words('english')]
        print('去除停止词后的注释词干list： ')
        print(tokens1)
        print('去除停止词后的代码词干list： ')
        print(tokens2)
        print()
        # ———————————————————————————————————————————————————————— 去除停止词 tokens1/2为词干为去除停止词后的词干

        import random

        comment_document = ''
        code_document = ''
        # print(len(tokens1))
        # print(len(tokens2))
        print()
        if(len(tokens1) != 0 and len(tokens2) != 0):
            for word in tokens1:
                randnum1 = random.randrange(0,len(tokens2))
                randnum2 = random.randrange(0,len(tokens2))
                if(comment_document == ''):
                    comment_document = comment_document + word + ' ' + tokens2[randnum1] + ' ' + tokens2[randnum2]
                if(comment_document != ''):
                    comment_document = comment_document + ' ' + word + ' ' + tokens2[randnum1] + ' ' + tokens2[randnum2]
            for word in tokens2:
                randnum1 = random.randrange(0,len(tokens1))
                randnum2 = random.randrange(0,len(tokens1))
                if(code_document == ''):
                    code_document = code_document + word + ' ' + tokens1[randnum1] + ' ' + tokens1[randnum2]
                elif(code_document != ''):
                    code_document = code_document + ' ' + word + ' ' + tokens1[randnum1] + ' ' + tokens1[randnum2]
            print('comment_document:  '+comment_document)
            print('code_document:  '+code_document)
            w.write(comment_document)
            w.write('\n')
            w.write(code_document)
        w.close()

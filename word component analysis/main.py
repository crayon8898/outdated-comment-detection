# This is a sample Python script.
# 词性标注
import re
import random
from nltk import word_tokenize,pos_tag,data
from nltk.corpus import stopwords
from decimal import Decimal

from nltk.tokenize import word_tokenize
import re, math
from collections import Counter
import os

WORD = re.compile(r'\w+')
stop_words = set(stopwords.words('english'))
data.path.append(r'/root/nltk_data')

from os import path
def get_cosine(vec1, vec2):
     intersection = set(vec1.keys()) & set(vec2.keys())
     numerator = sum([vec1[x] * vec2[x] for x in intersection])

     sum1 = sum([vec1[x]**2 for x in vec1.keys()])
     sum2 = sum([vec2[x]**2 for x in vec2.keys()])
     denominator = math.sqrt(sum1) * math.sqrt(sum2)

     if not denominator:
        return 0.0
     else:
        return float(numerator) / denominator
def text_to_vector(text):
     words = WORD.findall(text)
     return Counter(words)
def scaner_file(url):
    file = os.listdir(url)
    for f in file:
        real_url = path.join(url, f)
        if path.isfile(real_url):
            pathname=str(path.abspath(real_url))
            if(pathname.endswith('.java')):
                print(pathname)
                filepath=pathname

                comment = ''
                code = ''
                index = 0
                with open(filepath, 'r') as f:
                    for line in f:
                        index = index + 1
                        if index < 30:
                            if line[0].isdigit():
                                code += line
                            else:
                                comment += line
                # print(comment)
                # print(code)
                remove_chars = '[·’!"\#$%&\'()＃！（）*+,-./:;<=>?\@，：?￥★、…．＞【】［］《》？“”‘’\[\\]^_`{|}~]+'

                def get_tokens(sentence):
                    sentence = re.sub(remove_chars, "", sentence)
                    words = word_tokenize(sentence)
                    return words

                comments = get_tokens(comment)
                codes = get_tokens(code)



                def get_pos(words):
                    return pos_tag(words)

                comment_pos = get_pos(comments)
                code_pos = get_pos(codes)
                # print(comment_pos)
                # print(code_pos)
                cmt = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ]
                cd = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                cmtset = {'cmtsettest1'}
                cdset = {'cdsettest1'}
                for i in range(0, len(comment_pos)):
                    for j in range(0, len(comment_pos[i])):
                        if (j == 0 and comment_pos[i][j] not in cmtset):
                            cmtset.add(comment_pos[i][j])
                        if (str(comment_pos[i][j]) == 'NN' or
                                str(comment_pos[i][j]) == 'NNS' or
                                str(comment_pos[i][j]) == 'NNP' or
                                str(comment_pos[i][j]) == 'NNPS'):
                            cmt[0] = cmt[0] + 1
                        if (str(comment_pos[i][j]) == 'VB' or
                                str(comment_pos[i][j]) == 'VBD' or
                                str(comment_pos[i][j]) == 'VBG' or
                                str(comment_pos[i][j]) == 'VBN' or
                                str(comment_pos[i][j]) == 'VBP' or
                                str(comment_pos[i][j]) == 'VBZ'):
                            cmt[1] = cmt[1] + 1
                        if (str(comment_pos[i][j]) == 'DT' or
                                str(comment_pos[i][j]) == 'WDT'):
                            cmt[2] = cmt[2] + 1
                        if (str(comment_pos[i][j]) == 'IN' or
                                str(comment_pos[i][j]) == 'CC'):
                            cmt[3] = cmt[3] + 1
                        if (str(comment_pos[i][j]) == 'JJ' or
                                str(comment_pos[i][j]) == 'JJR' or
                                str(comment_pos[i][j]) == 'JJS'):
                            cmt[4] = cmt[4] + 1
                        if (str(comment_pos[i][j]) == 'RB' or
                                str(comment_pos[i][j]) == 'RBR' or
                                str(comment_pos[i][j]) == 'RBS' or
                                str(comment_pos[i][j]) == 'WRB'):
                            cmt[5] = cmt[5] + 1
                        if (str(comment_pos[i][j]) == 'PRP' or
                                str(comment_pos[i][j]) == 'PRP$' or
                                str(comment_pos[i][j]) == 'WP' or
                                str(comment_pos[i][j]) == 'WP$'):
                            cmt[6] = cmt[6] + 1
                        if (str(comment_pos[i][j]) == 'MD'):
                            cmt[7] = cmt[7] + 1
                        if (str(comment_pos[i][j]) == 'LS'):
                            cmt[8] = cmt[8] + 1
                        if (str(comment_pos[i][j]) == 'RP'):
                            cmt[9] = cmt[9] + 1
                for i in range(0, len(code_pos)):
                    for j in range(0, len(code_pos[i])):
                        if (j == 0 and code_pos[i][j] not in cdset):
                            cdset.add(code_pos[i][j])
                        if (str(code_pos[i][j]) == 'NN' or
                                str(code_pos[i][j]) == 'NNS' or
                                str(code_pos[i][j]) == 'NNP' or
                                str(code_pos[i][j]) == 'NNPS'):
                            cd[0] = cd[0] + 1
                        if (str(code_pos[i][j]) == 'VB' or
                                str(code_pos[i][j]) == 'VBD' or
                                str(code_pos[i][j]) == 'VBG' or
                                str(code_pos[i][j]) == 'VBN' or
                                str(code_pos[i][j]) == 'VBP' or
                                str(code_pos[i][j]) == 'VBZ'):
                            cd[1] = cd[1] + 1
                        if (str(code_pos[i][j]) == 'DT' or
                                str(code_pos[i][j]) == 'WDT'):
                            cd[2] = cd[2] + 1
                        if (str(code_pos[i][j]) == 'IN' or
                                str(code_pos[i][j]) == 'CC'):
                            cd[3] = cd[3] + 1
                        if (str(code_pos[i][j]) == 'JJ' or
                                str(code_pos[i][j]) == 'JJR' or
                                str(code_pos[i][j]) == 'JJS'):
                            cd[4] = cd[4] + 1
                        if (str(code_pos[i][j]) == 'RB' or
                                str(code_pos[i][j]) == 'RBR' or
                                str(code_pos[i][j]) == 'RBS' or
                                str(code_pos[i][j]) == 'WRB'):
                            cd[5] = cd[5] + 1
                        if (str(code_pos[i][j]) == 'PRP' or
                                str(code_pos[i][j]) == 'PRP$' or
                                str(code_pos[i][j]) == 'WP' or
                                str(code_pos[i][j]) == 'WP$'):
                            cd[6] = cd[6] + 1
                        if (str(code_pos[i][j]) == 'MD'):
                            cd[7] = cd[7] + 1
                        if (str(code_pos[i][j]) == 'LS'):
                            cd[8] = cd[8] + 1
                        if (str(code_pos[i][j]) == 'RP'):
                            cd[9] = cd[9] + 1
                print('注释成分:')
                cmtsum = sum(cmt)
                cdsum = sum(cd)
                for i in cmt:
                    if cmtsum!=0:
                        print(Decimal(float(i) / float(cmtsum)).quantize(Decimal("0.00")))

                print()
                for i in cd:
                    if cdsum!=0:
                        print(Decimal(float(i) / float(cdsum)).quantize(Decimal("0.00")))
                filtered_sentence_cmt = [w for w in cmtset if not w in stop_words]
                filtered_sentence_cd = [w for w in cdset if not w in stop_words]
                filtered_sentence_cd = [w for w in cdset if not w.isdigit()]

                print(filtered_sentence_cmt)
                print(filtered_sentence_cd)

                cmttxt=''
                cdtxt=''

                for i in filtered_sentence_cmt:
                    cmttxt=cmttxt+" "+i
                    random1 = random.randrange(0,len(filtered_sentence_cd))
                    random2 = random.randrange(0,len(filtered_sentence_cd))
                    cmttxt=cmttxt+" "+filtered_sentence_cd[random1]+" "+filtered_sentence_cd[random2]
                for i in filtered_sentence_cd:
                    cdtxt=cdtxt+" "+i
                    random1 = random.randrange(0,len(filtered_sentence_cmt))
                    random2 = random.randrange(0,len(filtered_sentence_cmt))
                    cdtxt=cdtxt+" "+filtered_sentence_cmt[random1]+" "+filtered_sentence_cmt[random2]
                text1 = cmttxt
                text2 = cdtxt
                vector1 = text_to_vector(text1)
                vector2 = text_to_vector(text2)
                cosine = get_cosine(vector1, vector2)

                print(text1)
                print(text2)
                print(cosine)
                print('有意义词共有对数:')
                # print(filtered_sentence_cmt)
                # print(filtered_sentence_cd)
                gongyou = [w for w in filtered_sentence_cmt if w in filtered_sentence_cd]
                print(len(gongyou))
                with open(filepath, 'a') as f:
                    for i in range(0, len(cmt)):
                        if cmtsum!=0:
                            f.write("\nCMT" + str(i) + ":" + str(
                                Decimal(float(cmt[i]) / float(cmtsum)).quantize(Decimal("0.00"))) + "\n")
                        if cmt==0:
                            f.write("\nCMT" + str(i) + ":0" + "\n")
                    for i in range(0, len(cd)):
                        if cdsum!=0:
                            f.write("\nCD" + str(i) + ":" + str(
                                Decimal(float(cd[i]) / float(cdsum)).quantize(Decimal("0.00"))) + '\n')
                        if cdsum==0:
                            f.write("\nCD" + str(i) + ":0" + '\n')
                    f.write("GY:" + str(len(gongyou)) + "\n")
                    f.write("COSINE:"+str(Decimal(cosine).quantize(Decimal("0.0000"))))
                f.close()
            # 如果是文件，则以绝度路径的方式输出
        elif path.isdir(real_url):
            # 如果是目录，则是地柜调研自定义函数 scaner_file (url)进行多次
            scaner_file(real_url)
        else:
            pass
scaner_file("/Users/crayon/IdeaProjects/getChanges_on_CCset/CCSet_changes/ByType")



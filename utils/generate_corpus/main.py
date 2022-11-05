import os
from nltk import word_tokenize
from utils.comment import *
import random


# 输入：文件路径
# 输出：从CCSet中返回新旧注释和代码，         return oldComment, oldCode, newComment, newCode
#       如果有空，返回None
def getCommentAndCode(filepath):
    with open(filepath) as f:
        reachOldComment = False
        reachOldCode = False
        reachNewComment = False
        reachNewCode = False
        oldComment = ''
        oldCode = ''
        newComment = ''
        newCode = ''
        for line in f.readlines():
            if line.startswith('oldComment:'):
                reachOldComment = True
                continue
            if line.startswith('oldCode:'):
                reachOldComment = False
                reachOldCode = True
                continue
            if line.startswith('newComment:'):
                reachOldCode = False
                reachNewComment = True
                continue
            if line.startswith('newCode:'):
                reachNewComment = False
                reachNewCode = True
                continue
            if line.startswith('startline:'):
                break
            line = line.strip();
            line = ''.join([ch if ch.isalpha() else ' ' for ch in line])
            line = re.sub('[\u4e00-\u9fa5]', '', line)
            if reachOldComment and line.strip() != '':
                oldComment += line + '\n'
            if reachOldCode and line.strip() != '':
                oldCode += line + '\n'
            if reachNewComment and line.strip() != '':
                newComment += line + '\n'
            if reachNewCode and line.strip() != '':
                newCode += line + '\n'
        oldComment = oldComment.replace('\n', '', -1)
        oldCode = oldCode.replace('\n', '', -1)
        newComment = newComment.replace('\n', '', -1)
        newCode = newCode.replace('\n', '', -1)
        if oldComment == '' or oldCode == '' or newComment == '' or newCode == '':
            return None
        return oldComment, oldCode, newComment, newCode


# 输入 oldComment,oldCode,newComment,newCode
# 输出 Txt
def getTxt(oldComment, oldCode, newComment, newCode):
    tokenizedOldComment = word_tokenize(oldComment)
    tokenizedOldCode = word_tokenize(oldCode)
    tokenizedNewComment = word_tokenize(newComment)
    tokenizedNewCode = word_tokenize(newCode)
    oldCommentTxt = ''
    oldCodeTxt = ''
    newCommentTxt = ''
    newCodeTxt = ''
    for x in tokenizedOldComment:
        r1 = random.randint(0, len(tokenizedOldCode) - 1)
        r2 = random.randint(0, len(tokenizedOldCode) - 1)
        oldCommentTxt += x + ' ' + tokenizedOldCode[r1] + ' ' + tokenizedOldCode[r2] + ' '

    for x in tokenizedOldCode:
        r1 = random.randint(0, len(tokenizedOldComment) - 1)
        r2 = random.randint(0, len(tokenizedOldComment) - 1)
        oldCodeTxt += x + ' ' + tokenizedOldComment[r1] + ' ' + tokenizedOldComment[r2] + ' '

    for x in tokenizedNewComment:
        r1 = random.randint(0, len(tokenizedNewCode) - 1)
        r2 = random.randint(0, len(tokenizedNewCode) - 1)
        newCommentTxt += x + ' ' + tokenizedNewCode[r1] + ' ' + tokenizedNewCode[r2] + ' '

    for x in tokenizedNewCode:
        r1 = random.randint(0, len(tokenizedNewComment) - 1)
        r2 = random.randint(0, len(tokenizedNewComment) - 1)
        newCodeTxt += x + ' ' + tokenizedNewComment[r1] + ' ' + tokenizedNewComment[r2] + ' '

    return oldCommentTxt + '. ' + oldCodeTxt + '. ' + newCommentTxt + '. ' + newCodeTxt + '. '


# 处理复合词 调用cup
# 输入 txt
# 输出 处理复合词后的语料库txt
def useCupToGetTxt(txt):
    commentCleaner = CommentCleaner(replace_digit=True)
    javaDocDescPreprocessor = JavadocDescPreprocessor(comment_cleaner=commentCleaner)
    result = javaDocDescPreprocessor.preprocess_desc(txt, txt)
    res = ''
    for d in result:
        l = d['src_sent_tokens']
        for token in l:
            if not token.__contains__('<con>'):
                res += token + ' '
    return res


# 输入 ccset java文件路径
# 输出 由此文件生成的语料库
def generateTxt(filepath):
    res = getCommentAndCode(filepath)
    if not res is None:
        oldComment, oldCode, newComment, newCode = res
        txt = getTxt(oldComment, oldCode, newComment, newCode)
        txt = useCupToGetTxt(txt)
        return txt
    else:
        return ''


# 输入：路径
# 便利文件夹
def blwjj(filepath):
    if os.path.isdir(filepath):
        for f in os.listdir(filepath):
            blwjj(os.path.join(filepath, f))
    else:
        if os.path.abspath(filepath).endswith('.java'):
            print(os.path.abspath(filepath))
            with open('/home/chenyinan/DataSet/My/语料库/yuliao.txt', 'a') as f:
                txtSlip = generateTxt(os.path.abspath(filepath))
                print(txtSlip)
                f.write(txtSlip + '\n')


blwjj('/home/chenyinan/DataSet/My/CCSet/')


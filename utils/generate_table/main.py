
import csv
import os
from os import path


title = ['label','changeNum','attribute','methodDeclaration','methodRenaming',
         'returnType','parameterDelete','parameterInsert',
         'parameterRenaming','parameterTypeChange','containReturn',
         'lineNumOfOldCode','lineNumOfOldComment','lineNumOfNewCode','TODOCount','FIXMECount','XXXCount',
         'BUGCount','VERSIONCount','FIXEDCount','commentByCCSet',
         'lineNumOfChanged','changedLineByAllCodeLine','ifInsert','ifUpdate',
         'ifMove','ifDelete','forInsert','forUpdate',
         'forMove','forDelete','foreachInsert',
         'foreachUpdate','foreachMove','foreachDelete',
         'whileInsert','whileUpdate','whileMove','whileDelete',
         'catchInsert','catchUpdate','catchMove','catchDelete',
         'tryInsert','tryUpdate','tryMove','tryDelete',
         'throwInsert','throwUpdate','throwMove','throwDelete',
         'methodInvInsert','methodInvUpdate','methodInvMove','methodInvDelete','assignInsert',
         'assignUpdate','assignMove','assignDelete',
         'varDecInsert','varDecUpdate','varDecMove',
         'varDecDelete','elseInsert','elseUpdate','elseMove','elseDelete',
         'NN','VB','DT','IN','JJ',
         'RB','PRP','MD','LS',
         'RP','NN','VB','DT','IN','JJ',
         'RB','PRP','MD','LS','RP','bothHavePairNumChange','cmt2cd_sim_before','cmt2cd_sim_after','cmt2ch_sim','sim_change','all_token_change_sim']



# 打开csv文件
with open("/Users/chenyn/chenyn's/研究生/DataSet/My/csv/features_10_23_drop_duplicates.csv",'w',newline='')as csv_file:
    # 获取一个csv对象进行内容写入
    writer = csv.writer(csv_file)
    writer.writerow(title)

    url = "/Users/chenyn/chenyn's/研究生/DataSet/My/features/features_10_23_drop_duplicates"
    file = os.listdir(url)
    for f in file:
        real_url = path.join(url, f)
        if path.isfile(real_url):
            pathname = str(path.abspath(real_url))
            if (pathname.endswith('.java')):
                with open(pathname, 'r') as f:
                    row=[]
                    for line in f:
                        if line.__contains__(':'):
                            print(line.split(':')[1])
                            row.append(str(line.split(':')[1]).replace('\n',''))
                        if line.startswith('all_token_change_sim:'):
                            break
                    print(row)
                    writer.writerow(row)
